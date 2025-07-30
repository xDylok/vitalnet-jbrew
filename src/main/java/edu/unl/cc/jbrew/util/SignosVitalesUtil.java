package edu.unl.cc.jbrew.util;

public class SignosVitalesUtil {
    public static boolean isPresionOutOfRange(String presionSign, String presionNormal) {
        try {
            String[] normalParts = presionNormal.split("/");
            String[] signParts = presionSign.split("/");

            if (normalParts.length != 2 || signParts.length != 2) {
                return false;
            }

            int normalSistolica = Integer.parseInt(normalParts[0].trim());
            int normalDiastolica = Integer.parseInt(normalParts[1].trim());

            int signSistolica = Integer.parseInt(signParts[0].trim());
            int signDiastolica = Integer.parseInt(signParts[1].trim());

            int tolerancia = 10;

            boolean sistolicaFuera = signSistolica < (normalSistolica - tolerancia) || signSistolica > (normalSistolica + tolerancia);
            boolean diastolicaFuera = signDiastolica < (normalDiastolica - tolerancia) || signDiastolica > (normalDiastolica + tolerancia);

            return sistolicaFuera || diastolicaFuera;

        } catch (NumberFormatException e) {
            return false;
        }
    }
}
