package ru.xxi_empire.rkhunter.distancebetweentwocities.helpers;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by rkhunter on 12/25/16.
 */

public class Calculator {
    public double Distance(LatLng a, LatLng b) {
        /*
            Haversine formula:
            a = sin²(Δφ/2) + cos φ1 ⋅ cos φ2 ⋅ sin²(Δλ/2)
            c = 2 ⋅ atan2( √a, √(1−a) )
            d = R ⋅ c
            where	φ is latitude, λ is longitude, R is earth’s radius (mean radius = 6,371km);
            note that angles need to be in radians to pass to trig functions!
        */
        double R = 6371e3;
        double φ1 = Math.toRadians(a.latitude);
        double φ2 = Math.toRadians(b.latitude);
        double Δφ = Math.toRadians(b.latitude - a.latitude);
        double Δλ = Math.toRadians(b.longitude - a.longitude);

        double _a = Math.sin(Δφ/2) * Math.sin(Δφ/2) + Math.cos(φ1) * Math.cos(φ2) * Math.sin(Δλ/2) * Math.sin(Δλ/2);

        double _c = 2 * Math.atan2(Math.sqrt(_a), Math.sqrt(1-_a));

        double _d = R * _c;

        return _d;
    }

    public LatLng Midpoint(LatLng a, LatLng b) {
        /*
            Formula:
            Bx = cos φ2 ⋅ cos Δλ
            By = cos φ2 ⋅ sin Δλ
            φm = atan2( sin φ1 + sin φ2, √(cos φ1 + Bx)² + By² )
            λm = λ1 + atan2(By, cos(φ1)+Bx)
            where	φ is latitude, λ is longitude;
            note that angles need to be in radians to pass to trig functions!
         */
        double φ1 = Math.toRadians(a.latitude);
        double λ1 = Math.toRadians(a.longitude);

        double φ2 = Math.toRadians(b.latitude);
        double λ2 = Math.toRadians(b.longitude);

        double Δλ = λ2 - λ1;


        double Bx = Math.cos(φ2) * Math.cos(Δλ);
        double By = Math.cos(φ2) * Math.sin(Δλ);

        double φ3 = Math.atan2(
                Math.sin(φ1) + Math.sin(φ2),
                Math.sqrt( (Math.cos(φ1)+Bx)*(Math.cos(φ1)+Bx) + By*By )
        );
        double λ3 = λ1 + Math.atan2(By, Math.cos(φ1) + Bx);

        return new LatLng(Math.toDegrees(φ3), Math.toDegrees(λ3));
    }
}
