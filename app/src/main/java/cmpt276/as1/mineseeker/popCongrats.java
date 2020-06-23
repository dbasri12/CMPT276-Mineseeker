package cmpt276.as1.mineseeker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

public class popCongrats extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_congrats);

        DisplayMetrics dm =new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width=dm.widthPixels;
        int height=dm.heightPixels;

        getWindow().setLayout((int)(width*.5),(int)(height*.5));
        setupOKButton();
    }
    public static Intent makeIntent(Context context){
        return new Intent(context,popCongrats.class);
    }
    private void setupOKButton(){
        Button okBtn=(Button)findViewById(R.id.buttonOK);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=HomeActivity.makeIntent(popCongrats.this);
                startActivity(intent);
            }
        });
    }
}