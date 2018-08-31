package bm.pdm.ifpb.com.businessmanager.infra;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {

    public boolean verificarConexao(Context context){
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // Objeto netInfo que recebe as informacoes da Network
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //Se o objeto for nulo ou nao tem conectividade retorna false
        if ((netInfo != null) && (netInfo.isConnectedOrConnecting())
                && (netInfo.isAvailable())){
            return true;
        } else {
            return false;
        }
    }

}
