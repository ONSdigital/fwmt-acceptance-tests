package uk.gov.ons.fwmt.acceptancetests.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class JobDto {

  private String tmJobId;

  private String lastAuthNo;

  private LocalDateTime lastUpdated;

}

