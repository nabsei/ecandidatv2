package co.simplon.ecandidat.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MontpellierApiResponse {

    @JsonProperty("total_count")
    private Integer totalCount;

    @JsonProperty("results")
    private List<MontpellierFormationDto> results;

    public MontpellierApiResponse() {
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<MontpellierFormationDto> getResults() {
        return results;
    }

    public void setResults(List<MontpellierFormationDto> results) {
        this.results = results;
    }
}
