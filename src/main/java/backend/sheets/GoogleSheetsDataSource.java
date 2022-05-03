package backend.sheets;

import backend.sheets.response.Result;
import backend.sheets.response.TotalHoursSheetsModel;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import util.Constants;
import util.Log;
import util.Util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GoogleSheetsDataSource {
    private static final String APPLICATION_NAME = "Google Sheets API Cleanup Coordinator";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "./credentials.json";

    private Sheets service;

    public GoogleSheetsDataSource() {
        // Build a new authorized API client service.
        try {
            NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        } catch (Exception e) {
            Log.e(e.getMessage(), e);
        }
    }

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = new FileInputStream(CREDENTIALS_FILE_PATH);
        ;
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public Result<Map<String, String>> getSlackUserToName() {
        try {
            ValueRange response = getValueRange(Constants.getSheetsMemberRange());
            if (response == null) {
                return Result.error("Error getting values");
            }
            return Result.ok(getMapFromUserToNameValues(response.getValues()));
        } catch (IOException e) {
            Log.e(e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    private ValueRange getValueRange(String range) throws IOException {
        return service.spreadsheets().values()
                .get(Constants.getSheetsFile(), range)
                .execute();
    }

    private Map<String, String> getMapFromUserToNameValues(List<List<Object>> values) {
        return values.stream()
                .collect(Collectors.toMap(
                        row -> row.get(1).toString(), //Slack User ID
                        row -> row.get(0).toString() //Name
                ));
    }

    public Result<List<TotalHoursSheetsModel>> getTotalHours() {
        try {
            ValueRange response = getValueRange(Constants.getSheetsTotalHoursRange());
            return Result.ok(getTotalHoursFromValues(response.getValues()));
        } catch (IOException e) {
            Log.e(e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    private List<TotalHoursSheetsModel> getTotalHoursFromValues(List<List<Object>> values) {
        return values.stream().map(row -> {
            var name = row.get(0).toString();
            var completedHours = Util.parseIntSafely(row.get(1).toString());
            var requiredHours = Util.parseIntSafely(row.get(2).toString());
            return new TotalHoursSheetsModel(name, completedHours, requiredHours);
        }).collect(Collectors.toList());
    }
}
