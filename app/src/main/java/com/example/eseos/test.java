/*package com.example.eseos;

package fr.eseo.projetandroids9.network;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import fr.eseo.projetandroids9.controller.JuryActivity;
import fr.eseo.projetandroids9.controller.ProjectActivity;
import fr.eseo.projetandroids9.model.Liprj;
import fr.eseo.projetandroids9.model.Myjur;

public class RequestHandle extends AsyncTask <String, Void, JSONObject> {

    private SSLContext context;
    private SessionManager sessionManager;
    private Context activityContext;
    private String request = "";

    private boolean connexion;
    private boolean juryList;

    private String urlStart = "https://192.168.4.240/pfe/webservice.php?q=";

    public RequestHandle(SSLContext current, SessionManager sessionManager, Context activityContext) {
        this.context = current;
        this.sessionManager = sessionManager;
        this.activityContext = activityContext;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        JSONObject jsonObject = new JSONObject();
        try {
            request = strings[0];
            connexion = false;
            juryList = false;
            switch(strings[0]){
                case "LOGON" :
                    connexion = true;
                    Log.d("SWITCH 1", "LOGON");
                    urlStart+="LOGON&user="+sessionManager.getId()+"&pass="+sessionManager.getPassword();
                    Log.d("URL", urlStart);
                    break;
                case "LIPRJ" :
                    Log.d("SWITCH 1","LIPRJ");
                    urlStart+="LIPRJ&user="+sessionManager.getId()+"&token="+sessionManager.getToken();
                    break;
                case "MYJUR" :
                    juryList = true;
                    urlStart+="MYJUR&user="+sessionManager.getId()+"&token="+sessionManager.getToken();
                    Log.d("SWITCH 1", "MYJUR");
                    break;
                default:
                    break;
            }
            URL url = new URL(urlStart);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setSSLSocketFactory(context.getSocketFactory());
            InputStream in = urlConnection.getInputStream();
            StringWriter writer = new StringWriter();
            IOUtils.copy(in, writer, "UTF-8");
            String result = writer.toString();
            try {
                jsonObject = new JSONObject(result);
            } catch (JSONException e){
                e.printStackTrace();
            }

            return jsonObject;

        } catch (Exception e) {
            return jsonObject;
        }
    }

    @Override
    protected void onPostExecute(JSONObject s) {
        super.onPostExecute(s);
        Log.d("SWITCH 3 deb",request);
        switch(request) {
            case "LOGON":
                Log.d("SWITCH 3","LOGON");
                try {
                    if (s.getString("result").equals("OK")){
                        sessionManager.createToken(s.getString("token"));
                        Intent intent = new Intent(activityContext, ProjectActivity.class);
                        activityContext.startActivity(intent);
                    } else {
                        Toast.makeText(activityContext, "Erreur, mauvais identifiant ou mot de passe", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case "MYJUR":
                Log.d("SWITCH 3","MYJUR");
                if (s.length() != 0){

                    Myjur myJur = new Myjur(s);
                    try {
                        sessionManager.createJury(myJur.getJury()); //create juries
                        sessionManager.createJuryDate(myJur.getJuryDate());
                        sessionManager.createJuryProjects(myJur.getProject()); //create associate projects
                        sessionManager.createJuryConfid(myJur.getConfid());
                        sessionManager.createJuryPosterLocation(myJur.getPosterLocation());
                        sessionManager.createJuryId(myJur.getProjectId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(activityContext, JuryActivity.class);
                    activityContext.startActivity(intent);
                } else {
                    Toast.makeText(activityContext, "Accès refusé, vous n'êtes pas membre d'un jury", Toast.LENGTH_SHORT).show();
                }
                break;

            case "LIPRJ":
                Log.d("SWITCH 3","LIPRJ");
                Liprj liprj = new Liprj(s);

                try {
                    sessionManager.createProjectTitles(liprj.getProjects().get(0)); //0 for titles
                    sessionManager.createProjectDesc(liprj.getProjects().get(1)); //1 for descriptions
                    sessionManager.createProjectConfid(liprj.getProjects().get(2)); //2 for confid
                    sessionManager.createProjectPoster(liprj.getProjects().get(3)); //3 for poster
                    sessionManager.createProjectSupervisor(liprj.getProjects().get(4)); //4 for supervisor
                    sessionManager.createProjectStudents(liprj.getStudents()); //students
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(activityContext, ProjectActivity.class);
                activityContext.startActivity(intent);
                break;

            default:

                Log.d("SWITCH 3","default");
                break;
        }
    }
}*/