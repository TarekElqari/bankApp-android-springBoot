package ma.emsi.bank_mobie.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ma.emsi.bank_mobie.R;
import ma.emsi.bank_mobie.models.Compte;

public class CompteAdapter extends RecyclerView.Adapter<CompteAdapter.AccountViewHolder> {
    private final List<Compte> accounts;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEdit(Compte compte);
        void onDelete(Compte compte);
    }

    public CompteAdapter(List<Compte> accounts, OnItemClickListener listener) {
        this.accounts = accounts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.account_item, parent, false);
        return new AccountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        holder.bind(accounts.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

    static class AccountViewHolder extends RecyclerView.ViewHolder {
        private final TextView accountInfo;
        private final ImageButton editButton;
        private final ImageButton deleteButton;

        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
            accountInfo = itemView.findViewById(R.id.accountInfo);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }

        @SuppressLint("SetTextI18n")
        public void bind(Compte compte, OnItemClickListener listener) {
            accountInfo.setText("ID: " + compte.getId() + "\nBalance: " +
                    compte.getSolde() + "\nType: " + compte.getType());
            editButton.setOnClickListener(v -> listener.onEdit(compte));
            deleteButton.setOnClickListener(v -> listener.onDelete(compte));
        }
    }
}
