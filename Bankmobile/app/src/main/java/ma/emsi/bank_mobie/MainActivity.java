package ma.emsi.bank_mobie;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ma.emsi.bank_mobie.adapter.CompteAdapter;
import ma.emsi.bank_mobie.api.CompteApi;
import ma.emsi.bank_mobie.config.RetrofitClient;
import ma.emsi.bank_mobie.dialogs.AccountDialog;
import ma.emsi.bank_mobie.models.Compte;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Spinner formatSpinner;
    private Button addAccountButton;
    private CompteAdapter accountAdapter;
    private List<Compte> compteList = new ArrayList<>();
    private boolean useJson = true;
    private CompteApi compteApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.accountRecyclerView);
        formatSpinner = findViewById(R.id.formatSpinner);
        addAccountButton = findViewById(R.id.addAccountButton);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        accountAdapter = new CompteAdapter(compteList, new CompteAdapter.OnItemClickListener() {
            @Override
            public void onEdit(Compte compte) {
                AccountDialog accountDialog = new AccountDialog(compte, updatedCompte -> editAccount(updatedCompte));
                accountDialog.show(getSupportFragmentManager(), "AccountDialog");
            }

            @Override
            public void onDelete(Compte compte) {
                deleteAccount(compte.getId());
            }
        });


        recyclerView.setAdapter(accountAdapter);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.format_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        formatSpinner.setAdapter(adapter);

        // Handle format selection
        formatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, android.view.View selectedItemView, int position, long id) {
                useJson = (position == 0);
                loadApiClient();
                loadAccounts();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // No action needed
            }
        });
        loadApiClient();
        loadAccounts();
        addAccountButton.setOnClickListener(v -> {
            Compte newCompte = new Compte(null, 0.0, "COURANT");
            // Add the account
            AccountDialog accountDialog = new AccountDialog(newCompte, this::createAccount);
            accountDialog.show(getSupportFragmentManager(), "AccountDialog");
        });
    }


    public void onEdit(Compte compte) {
        AccountDialog accountDialog = new AccountDialog(compte, this::editAccount);
        accountDialog.show(getSupportFragmentManager(), "AccountDialog");
    }




    private void loadApiClient() {
        Retrofit retrofit = useJson ? RetrofitClient.getJsonClient() : RetrofitClient.getXmlClient();
        compteApi = retrofit.create(CompteApi.class);
    }

    private void loadAccounts() {
        compteApi.getAllComptes("application/" + (useJson ? "json" : "xml")).enqueue(new Callback<List<Compte>>() {
            @Override
            public void onResponse(Call<List<Compte>> call, Response<List<Compte>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    compteList.clear();
                    compteList.addAll(response.body());
                    accountAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to load accounts", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Compte>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createAccount(Compte compte) {
        compteApi.createCompte(compte, "application/" + (useJson ? "json" : "xml")).enqueue(new Callback<Compte>() {
            @Override
            public void onResponse(Call<Compte> call, Response<Compte> response) {
                if (response.isSuccessful() && response.body() != null) {
                    compteList.add(response.body());
                    accountAdapter.notifyItemInserted(compteList.size() - 1);
                } else {
                    Toast.makeText(MainActivity.this, "Failed to create account", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Compte> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void editAccount(Compte compte) {
        compteApi.updateCompte(compte.getId(), compte, "application/" + (useJson ? "json" : "xml")).enqueue(new Callback<Compte>() {
            @Override
            public void onResponse(Call<Compte> call, Response<Compte> response) {
                if (response.isSuccessful() && response.body() != null) {
                    loadAccounts();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to update account", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Compte> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteAccount(Long id) {
        compteApi.deleteCompte(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    loadAccounts();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to delete account", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
