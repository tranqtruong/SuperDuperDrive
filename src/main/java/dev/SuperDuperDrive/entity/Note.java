package dev.SuperDuperDrive.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Note {
    private Integer noteid;
    private String notetitle;
    private String notedescription;
    private String email;
}
