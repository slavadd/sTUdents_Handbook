package com.example.timetablenew.maps;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.timetablenew.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    FloatingActionButton fabInfo;
    String value ="https://www.tu-sofia.bg/kcfinder-master/upload/%D0%A4%D0%90/files/map%20campus.pdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        fabInfo = findViewById(R.id.fabInfo);

        //https://www.tu-sofia.bg/kcfinder-master/upload/%D0%A4%D0%90/files/map%20campus.pdf

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fabInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //second option
                Intent intent = new Intent(MapsActivity.this, CampusActivity.class);
                startActivity(intent);

//                String viewerUrl = "https://docs.google.com/viewer?url=";
//                String pdfViewerURL = "https://drive.google.com/viewerng/viewer?embedded=true&url=";
//                String urlView= "https://docs.google.com/viewerng/viewer?url=";
//                SemesterPdfActivity.setChangeUrl(urlView + value);
//                Intent intent = new Intent(MapsActivity.this, SemesterPdfActivity.class);
//                startActivity(intent);
            }
        });


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setScrollGesturesEnabledDuringRotateOrZoom(true);

        // Add a marker and move the camera
        LatLng tuSofia = new LatLng(42.657087300207344, 23.35536785397652);
        mMap.addMarker(new MarkerOptions().position(tuSofia).title(getString(R.string.first_block)));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(tuSofia, 17);
        mMap.animateCamera(cameraUpdate);
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        LatLng first1 = new LatLng(42.655943207024016, 23.356344178178187);
        LatLng first2 = new LatLng(42.65607734317613, 23.354917243065124);
        LatLng first3 = new LatLng(42.65814458140008, 23.355174735115604);
        LatLng first4 = new LatLng(42.658097240835595, 23.355700448051994);
        LatLng first5 = new LatLng(42.65765539382901, 23.355754092229173);
        LatLng first6 = new LatLng(42.65761594305075, 23.356558754886915);

        mMap.addPolyline((new PolylineOptions())
                .add(first1,first2, first3,first4,first5,first6,first1).width(5).color(Color.RED));



        LatLng tuSecondSofia = new LatLng(42.65719776326647, 23.354659750940513);
        mMap.addMarker(new MarkerOptions().position(tuSecondSofia).title(getString(R.string.second_block)));
        LatLng second1 = new LatLng(42.65603789139642, 23.354831412319957);
        LatLng second2 = new LatLng(42.65610890458132, 23.353973105485036);
        LatLng second3 = new LatLng(42.658207702096355, 23.354209139864636);
        LatLng second4 = new LatLng(42.658152471490354, 23.35506744669956);

        mMap.addPolyline((new PolylineOptions())
                .add(second1,second2, second3,second4,second1).width(5).color(Color.RED));

        LatLng tuThirdSofia = new LatLng(42.65571265980478, 23.354784473712655);
        mMap.addMarker(new MarkerOptions().position(tuThirdSofia).title(getString(R.string.third_block)));
        LatLng third1 = new LatLng(42.65577972816288, 23.355637416124246);
        LatLng third2 = new LatLng(42.65586652240487, 23.354044184061923);
        LatLng third3 = new LatLng(42.65538915257488, 23.353990539884737);
        LatLng third4 = new LatLng(42.655298412440644, 23.35559986520022);

        mMap.addPolyline((new PolylineOptions())
                .add(third1,third2, third3,third4,third1).width(5).color(Color.RED));

        LatLng tuForthSofia = new LatLng(42.654884162318325, 23.35418902335433);
        mMap.addMarker(new MarkerOptions().position(tuForthSofia).title(getString(R.string.forth_block)));

        LatLng forth1 = new LatLng(42.65538915257488, 23.353990539884737);
        LatLng forth2 = new LatLng(42.655298412440644, 23.35559986520022);
        LatLng forth3 = new LatLng(42.65452119803967, 23.355514034530746);
        LatLng forth4 = new LatLng(42.65461982984672, 23.353920802468416);

        mMap.addPolyline((new PolylineOptions())
                .add(forth1,forth2, forth3,forth4,forth1).width(5).color(Color.RED));



        LatLng tuSeventhSofia = new LatLng(42.654931982262056, 23.356053486927745);
        mMap.addMarker(new MarkerOptions().position(tuSeventhSofia).title(getString(R.string.seventh_block)));

        LatLng seventh1 = new LatLng(42.65530922580299, 23.35633449189315);
        LatLng seventh2 = new LatLng(42.6547187566405, 23.356272046338038);
        LatLng seventh3 = new LatLng(42.65472531743976, 23.35586168990441);
        LatLng seventh4 = new LatLng(42.6553289080109, 23.355906293864585);

        mMap.addPolyline((new PolylineOptions())
                .add(seventh1,seventh2, seventh3,seventh4,seventh1).width(5).color(Color.RED));


        LatLng tuNinthSofia = new LatLng(42.652701277994765, 23.355803704772455);
        mMap.addMarker(new MarkerOptions().position(tuNinthSofia).title(getString(R.string.ninth_block)));

        LatLng ninth1 = new LatLng(42.65284562010098, 23.355094501805635);
        LatLng ninth2 = new LatLng(42.652757046575516, 23.35618729882998);
        LatLng ninth3 = new LatLng(42.65179585168712, 23.356107011701663);
        LatLng ninth4 = new LatLng(42.65181553500786, 23.35500083348926);

        mMap.addPolyline((new PolylineOptions())
                .add(ninth1,ninth2, ninth3,ninth4,ninth1).width(5).color(Color.RED));


        LatLng tuTenthSofia = new LatLng(42.652681192946226, 23.35450970382127);
        mMap.addMarker(new MarkerOptions().position(tuTenthSofia).title(getString(R.string.tenth_block)));

        LatLng tenth1 = new LatLng(42.65283934703329, 23.35432937551786);
        LatLng tenth2 = new LatLng(42.65279980399449, 23.35493991226322);
        LatLng tenth3 = new LatLng(42.65259945221138, 23.3549231852291);
        LatLng tenth4 = new LatLng(42.652641631587784, 23.354301895390382);

        mMap.addPolyline((new PolylineOptions())
                .add(tenth1,tenth2, tenth3,tenth4,tenth1).width(5).color(Color.RED));


        LatLng tuEighthSofia = new LatLng(42.6523182158088, 23.35429801570348);
        mMap.addMarker(new MarkerOptions().position(tuEighthSofia).title(getString(R.string.eighth_block)));

        LatLng eighth1 = new LatLng(42.65251745895625, 23.354922970381217);
        LatLng eighth2 = new LatLng(42.65256874917009, 23.354107578895672);
        LatLng eighth3 = new LatLng(42.65279955459919, 23.35413708319312);
        LatLng eighth4 = new LatLng(42.652809418059974, 23.353796442668013);
        LatLng eighth5 = new LatLng(42.65197496374309, 23.353643556755408);
        LatLng eighth6 = new LatLng(42.65190394583764, 23.354837139697725);

        mMap.addPolyline((new PolylineOptions())
                .add(eighth1,eighth2, eighth3,eighth4,eighth5,eighth6, eighth1).width(5).color(Color.RED));


        LatLng tuThirteenthSofia = new LatLng(42.650090600392765, 23.35515005736908);
        mMap.addMarker(new MarkerOptions().position(tuThirteenthSofia).title(getString(R.string.thirteenth_block)));

        LatLng thirteenth1 = new LatLng(42.65033047969499, 23.355120575524325);
        LatLng thirteenth2 = new LatLng(42.649908996237926, 23.355059769219515);
        LatLng thirteenth3 = new LatLng(42.64987104903412, 23.35559228504042);
        LatLng thirteenth4 = new LatLng(42.65029930899051, 23.35564756349934);

        mMap.addPolyline((new PolylineOptions())
                .add(thirteenth1,thirteenth2, thirteenth3,thirteenth4,thirteenth1).width(5).color(Color.RED));


        LatLng tuTwelfthSofia = new LatLng(42.657989973260854, 23.35339036875823);
        mMap.addMarker(new MarkerOptions().position(tuTwelfthSofia).title(getString(R.string.twelfth_block)));
        LatLng twelfth1 = new LatLng(42.65825413379258, 23.35284766189582);
        LatLng twelfth2 = new LatLng(42.6579124476744, 23.35281252260257);
        LatLng twelfth3 = new LatLng(42.65785573908049, 23.353899888496866);
        LatLng twelfth4 = new LatLng(42.658184504629006, 23.353923314692366);

        mMap.addPolyline((new PolylineOptions())
                .add(twelfth1,twelfth2, twelfth3,twelfth4,twelfth1).width(5).color(Color.RED));


        LatLng tuSportSofia = new LatLng(42.6586239026218, 23.3549337016455);
        mMap.addMarker(new MarkerOptions().position(tuSportSofia).title(getString(R.string.sport_center)));

        LatLng sport1 = new LatLng(42.65840298138118, 23.355620347140235);
        LatLng sport2 = new LatLng(42.659124917523805, 23.355695448988293);
        LatLng sport3 = new LatLng(42.65918409224577, 23.354719124963562);
        LatLng sport4 = new LatLng(42.658454266738914, 23.35463329428007);

        mMap.addPolyline((new PolylineOptions())
                .add(sport1,sport2, sport3,sport4,sport1).width(5).color(Color.RED));

        LatLng tuEleventhSofia = new LatLng(42.65857656242129, 23.353914462305823);
        mMap.addMarker(new MarkerOptions().position(tuEleventhSofia).title(getString(R.string.eleventh_block)));

        LatLng eleventh1 = new LatLng(42.65850406105846, 23.353526741855195);
        LatLng eleventh2 = new LatLng(42.658817317242566, 23.353572905540133);
        LatLng eleventh3 = new LatLng(42.65878182524186, 23.35417723014297);
        LatLng eleventh4 = new LatLng(42.65846548260154, 23.354143656553923);

        mMap.addPolyline((new PolylineOptions())
                .add(eleventh1,eleventh2, eleventh3,eleventh4,eleventh1).width(5).color(Color.RED));

    }

}