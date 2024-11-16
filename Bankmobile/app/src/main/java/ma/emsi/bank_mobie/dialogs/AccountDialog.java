package ma.emsi.bank_mobie.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.DialogFragment;

import ma.emsi.bank_mobie.R;
import ma.emsi.bank_mobie.models.Compte;

public class AccountDialog extends DialogFragment {
    private EditText balanceEditText;
    private Spinner typeSpinner;
    private Button saveButton;
    private Button cancelButton;
    private Compte accountToEdit;


    public interface AccountDialogListener {
        void onAccountCreatedOrUpdated(Compte compte);
    }

    private AccountDialogListener listener;

    public AccountDialog(Compte accountToEdit, AccountDialogListener listener) {
        this.accountToEdit = accountToEdit;
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_account, container, false);

        balanceEditText = view.findViewById(R.id.balanceEditText);
        typeSpinner = view.findViewById(R.id.typeSpinner);
        saveButton = view.findViewById(R.id.saveButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.account_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);


        if (accountToEdit != null) {
            balanceEditText.setText(String.valueOf(accountToEdit.getSolde()));
            // Select the appropriate type
            String type = accountToEdit.getType();
            if (type.equals("COURANT")) {
                typeSpinner.setSelection(0);
            } else {
                typeSpinner.setSelection(1);
            }
        }

        saveButton.setOnClickListener(v -> {

            double balance = Double.parseDouble(balanceEditText.getText().toString());
            String type = typeSpinner.getSelectedItem().toString();
            if (accountToEdit != null) {
                accountToEdit.setSolde(balance);
                accountToEdit.setType(type);
                listener.onAccountCreatedOrUpdated(accountToEdit);
            } else {
                Compte newCompte = new Compte(null, balance, type);
                listener.onAccountCreatedOrUpdated(newCompte);
            }
            dismiss();
        });

        cancelButton.setOnClickListener(v -> dismiss());

        return view;
    }
}
