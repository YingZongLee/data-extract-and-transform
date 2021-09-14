package com.wcc.dataextract;

import lombok.Data;

@Data
public class Accountsecuritysetting {
    private String guid;
    private int iosenable;
    private int androidenable;
    private int windowsenable;
    private int macenable;
    private int webenable;
    private String account_guid;
    private int iosbindingenable;
    private int androidbindingenable;
    private int windowsbindingenable;
    private int macbindingenable;
}
