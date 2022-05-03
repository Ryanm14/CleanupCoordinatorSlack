package backend.sheets;

import backend.sheets.response.Result;
import com.google.api.services.sheets.v4.model.ValueRange;

public interface SheetsAPI {

    Result<ValueRange> getMembersSheet();

    Result<ValueRange> getTotalHoursSheet();

    Result<ValueRange> getKeysSheet();

    Result<ValueRange> getCleanupHours();
}
