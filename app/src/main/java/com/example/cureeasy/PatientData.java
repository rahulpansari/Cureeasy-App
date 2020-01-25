package com.example.cureeasy;

public class PatientData {
    String Pname, Dname, Cname, DisName, RTname, PTname, Bname, AppStatus, Ins;

    PatientData(String Pname, String Dname, String Cname, String DisName,
                String RTname, String PTname, String Bname, String AppStatus, String Ins ){
        this.Pname = Pname;
        this.Dname = Dname;
        this.Cname = Cname;
        this.DisName = DisName;
        this.RTname = RTname;
        this.PTname = PTname;
        this.Bname = Bname;
        this.AppStatus = AppStatus;
        this.Ins = Ins;
    }
}
