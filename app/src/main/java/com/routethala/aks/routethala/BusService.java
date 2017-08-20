package com.routethala.aks.routethala;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BusService {
    private VisionAPI visionAPI;
    private final MainActivity activity;

    private HashMap<String, List<String>> routes;

    private static final String TAG = BusService.class.getSimpleName();

    public BusService(VisionAPI visionAPI, MainActivity activity) {
        this.visionAPI = visionAPI;
        this.activity = activity;

        populateRoutes();
    }

    public void tagBus(Bitmap bitmap, String destination) {
        List<TextDetails> textDetailsList = visionAPI.identifyBus(bitmap);
        List<TextDetails> listOfTextWithBusNumber = filterBusNumbers(textDetailsList);
        List<BusResult> busResultList = getRouteResult(listOfTextWithBusNumber, destination);
        activity.drawBusResults(busResultList);
    }

    private List<TextDetails> filterBusNumbers(List<TextDetails> textDetailsList) {
        List<TextDetails> result = new ArrayList<>();
        List<String> routeNumbers = new ArrayList<>(routes.keySet());
        for(int i = 0; i < textDetailsList.size(); i++) {
            TextDetails textDetails = textDetailsList.get(i);
            Log.d(TAG, "---------- " + textDetails.getText());
            String matchingRoute = getMatchingRoute(routeNumbers, textDetails.getText());
            if(matchingRoute != null) {
                textDetails.setText(matchingRoute);
                result.add(textDetails);
            }
        }
        return result;
    }

    private List<BusResult> getRouteResult(List<TextDetails> textDetailsList, String destination) {
        List<BusResult> busResultList = new ArrayList<>();
        for(int i = 0; i < textDetailsList.size(); i++) {
            TextDetails textDetails = textDetailsList.get(i);
            if(routes.get(textDetails.getText()).contains(destination)) {
                busResultList.add(new BusResult(textDetails.getText(), true, textDetails.getBoundingPoly()));
            } else {
//                busResultList.add(new BusResult(textDetails.getText(), false, textDetails.getBoundingPoly()));
            }
        }
        return busResultList;
    }

    private String getMatchingRoute(List<String> list, String text) {
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).indexOf(text.toLowerCase()) > -1) {
                return list.get(i).toLowerCase();
            }
        }
        return null;
    }

    private void populateRoutes() {
        routes = new HashMap<>();

        routes.put("5c", new ArrayList<String>() {{
            add("Broadway"); add("Central"); add("Adyar Gate"); add("Kotturpuram"); add("Adyar");
            add("Taramani");
        }});

        routes.put("21h", new ArrayList<String>() {{
            add("Broadway"); add("Chepauk"); add("Adyar"); add("Karapakkam"); add("Navalur"); add("Kelambakkam");
        }});

        routes.put("571", new ArrayList<String>() {{
            add("Broadway"); add("Central"); add("Taylors Road"); add("Taylors Road"); add("Ambathur O.T.");
            add("Thirumullaivoyal"); add("Avadi"); add("Pattabiram"); add("Nemilicheri"); add("Ramapuram"); add("Thiruvallur");
        }});

        routes.put("38c", new ArrayList<String>() {{ add("T.V.K.Nagar"); add("Choolai P.O"); add("V.House"); }});
    }

}
