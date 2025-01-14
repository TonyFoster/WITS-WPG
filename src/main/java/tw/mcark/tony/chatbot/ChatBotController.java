package tw.mcark.tony.chatbot;

import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import okhttp3.*;

import java.io.IOException;

public class ChatBotController {

    public void ask(Context context) {
        UploadedFile uploadedImage = context.uploadedFile("image");
        String question = context.formParam("question");

        if (question == null) {
            context.status(400).result("Missing image or question");
            return;
        }

        // 2. Forward to Python service
        String pythonServiceUrl = "http://wits.tony19907051.com/lang-chain/ask";

        // Build multipart request
        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);


        if (uploadedImage != null) {
            byte[] content;
            try {
                content = uploadedImage.content().readAllBytes();
            } catch (IOException e) {
                context.status(500).result("Error reading image");
                return;
            }

            multipartBuilder.addFormDataPart(
                    "image",
                    uploadedImage.filename(),
                    RequestBody.create(content, MediaType.parse(uploadedImage.contentType()))
            );
        }


        // Add question
        multipartBuilder.addFormDataPart("question", question);

        RequestBody requestBody = multipartBuilder.build();

        // OkHttp client to call Python service
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(pythonServiceUrl)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                context.status(500).result("Error from lang chain service");
                return;
            }
            String responseBody = response.body().string();
            context.result(responseBody).contentType("application/json");
        } catch (IOException e) {
            context.status(500).result("Exception: " + e.getMessage());
        }
    }
}
