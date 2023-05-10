package Response.ImdbResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName(value = "results")
public class Movie {
    private String id;
    private Image image;
    private int runningTimeInMinutes;
    private String nextEpisode;
    private int numberOfEpisodes;
    private int seriesEndYear;
    private int seriesStartYear;
    private String title;
    private String titleType;
    private int year;
    private List<Principal> principals;
}