package bm.pdm.ifpb.com.businessmanager.infra;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

public class ContatosUtil {

    private ContentResolver resolver;

    public ContatosUtil(ContentResolver resolver){
        this.resolver = resolver;
    }

    public boolean verificarNumeroAgenda(String numero){
        boolean cond = false;
        Cursor c = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
        String auxConvert = numero.replace("-", "");
        int tam = auxConvert.length();
        String auxNumero = auxConvert.substring(tam - 8, tam);
        while (c.moveToNext()) {
            String phNumber = c.getString(c.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.NUMBER));
            if(phNumber.length() <= 5){
                System.out.println("Numero invalido...");
            } else {
                String convert = phNumber.replace("-", "");
                String auxContato = convert.substring(convert.length() - 8, convert.length());
                if (auxContato.equals(auxNumero)){
                    cond = true;
                    break;
                }
            }

        }
        return cond;
    }

}
