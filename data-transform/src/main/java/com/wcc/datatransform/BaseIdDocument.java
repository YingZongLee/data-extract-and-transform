package com.penpower.worldcard.team.mongo.document;

import com.penpower.worldcard.team.Utils.UUIDGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
public class BaseIdDocument {
    @Id
    private String guid;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        if(StringUtils.isEmpty(guid)) {
            this.guid = UUIDGenerator.getRandomUUID();
        } else {
            this.guid = guid;
        }
    }
}
