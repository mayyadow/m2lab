package dogapi;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * BreedFetcher implementation that relies on the dog.ceo API.
 * Note that all failures get reported as BreedNotFoundException
 * exceptions to align with the requirements of the BreedFetcher interface.
 */
public class DogApiBreedFetcher implements BreedFetcher {
    private final OkHttpClient client = new OkHttpClient();

    /**
     * Fetch the list of sub breeds for the given breed from the dog.ceo API.
     * @param breed the breed to fetch sub breeds for
     * @return list of sub breeds for the given breed
     * @throws BreedNotFoundException if the breed does not exist (or if the API call fails for any reason)
     */
    @Override
    public List<String> getSubBreeds(String breed) {
        // TODO Task 1: Complete this method based on its provided documentation
        //      and the documentation for the dog.ceo API. You may find it helpful
        //      to refer to the examples of using OkHttpClient from the last lab,
        //      as well as the code for parsing JSON responses.

        final Request request = new Request.Builder()
                .url(String.format("https://dog.ceo/api/breed/%s/list", breed))
                .build();
        // Hint: look at the API documentation to understand what the response looks like.
        try {
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());

            if (responseBody.getString("status").equals("success")) {
                final JSONArray subBreedsArray = responseBody.getJSONArray("message");
                final List<String> subBreeds = new ArrayList<>();
                for (int i = 0; i < subBreedsArray.length(); i++) {
                    subBreeds.add(subBreedsArray.getString(i));
                }

                return subBreeds;
            }
            return Collections.emptyList();
        }
        catch (IOException | JSONException event) {
            e.printStackTrace();
            return Collections.emptyList();
        }

    }
}