package dev.SuperDuperDrive.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class File {
    private Integer fileId;
    private String filename;
    private String contenttype;
    private String filesize;
    private byte[] filedata;
    private String email;
}
