package src.utils;

import java.io.IOException;

/**
 * Created by scs0574 on 10/27/15.
 */
public class ProtectedConfigFileLoadException extends IOException {


    public ProtectedConfigFileLoadException(String errorMessage, int code) {
        super(errorMessage+" Error Code "+code);


    }

}
