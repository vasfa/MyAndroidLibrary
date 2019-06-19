package ir.mlibs.varitynotification.RestApi;


import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.Date;
import java.util.List;

//import org.codehaus.jackson.annotate.JsonIgnore;


public class AndroidDataItems {

    public long Id;
    public List<String> DS;
    public List<Boolean> DB;
    public List<Long> DI;
    public List<AndroidDataItems> DL;
    public List<Date> DT;


    public Object File;


    public int R;
    public String RS;



    public static int RESULT_TRUE =1;
    public static int RESULT_FALSE =0;


    public static AndroidDataItems FalseResult(String msg)
    {
        AndroidDataItems dataItems =new AndroidDataItems();
       dataItems.Id=(long)-1;
        dataItems.R=RESULT_FALSE;
        dataItems.RS=msg;

       return dataItems;
    }
    public static AndroidDataItems TrueResult(String msg)
    {
        AndroidDataItems dataItems =new AndroidDataItems();
        dataItems.Id=(long)-1;
        dataItems.R=RESULT_TRUE;
        dataItems.RS=msg;

        return dataItems;
    }

    @JsonIgnore
    public boolean IsSuccess()
    {
        return R == RESULT_TRUE;
    }

    @JsonIgnore
    public boolean IsFaild()
    {
        return R == RESULT_FALSE;
    }
}
