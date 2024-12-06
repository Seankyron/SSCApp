package com.example.sscapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sscapp.adapters.ProductAdapter;
import com.example.sscapp.models.Product;
import java.util.ArrayList;
import java.util.List;

public class CatalogFragment extends Fragment implements ProductAdapter.OnProductClickListener {

    private RecyclerView productsRecyclerView;
    private List<Product> products;
    private OnProductSelectedListener mListener;

    public interface OnProductSelectedListener {
        void onProductSelected(Product product);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (getParentFragment() instanceof OnProductSelectedListener) {
            mListener = (OnProductSelectedListener) getParentFragment();
        } else {
            throw new RuntimeException(getParentFragment().toString() + " must implement OnProductSelectedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catalog, container, false);

        productsRecyclerView = view.findViewById(R.id.products_recycler_view);
        setupProducts();

        return view;
    }

    private void setupProducts() {
        products = new ArrayList<>();
        // Add sample products
        products.add(new Product("RDPRNTS 1", 299.00, "This collection brings the spirit and pride of being a RED SPARTAN at Batangas State University to life.", "https://scontent.fmnl13-2.fna.fbcdn.net/v/t39.30808-6/465730293_992271972927349_4323276180953319294_n.jpg?_nc_cat=106&ccb=1-7&_nc_sid=127cfc&_nc_eui2=AeFMuiETyF_os-DOrGya-6DkbIiwTVatg9psiLBNVq2D2qFZi_9VFlGK3YxTQCO7GZLKewr8nUD5F4kGHhMMen1t&_nc_ohc=F_wlgcBU7YUQ7kNvgHyyuTc&_nc_zt=23&_nc_ht=scontent.fmnl13-2.fna&_nc_gid=AEyqMiMrxuhr8WgE3WuitST&oh=00_AYDHMzpvmMJb8P-scQitYujU2_c9KWt2DDM_i4NSi9VjDg&oe=6758F605", "Available"));
        products.add(new Product("RDPRNTS 2", 299.00, "This collection brings the spirit and pride of being a RED SPARTAN at Batangas State University to life.", "https://scontent.fmnl13-4.fna.fbcdn.net/v/t39.30808-6/465690009_992271982927348_6837668490539289149_n.jpg?stp=dst-jpg_s600x600_tt6&_nc_cat=109&ccb=1-7&_nc_sid=127cfc&_nc_eui2=AeE8k0X-pUrszP1JFm-KTOeBXcdIIagEVD5dx0ghqARUPj1v5LTRJSgFqkMOUf-TL0nMJapyf8Z6k7ky5T8APRPt&_nc_ohc=BEBEl3e1hl0Q7kNvgGll2Ja&_nc_zt=23&_nc_ht=scontent.fmnl13-4.fna&_nc_gid=A16gTib8NODn269nmZTk3Nm&oh=00_AYB-EsifyD4QQz43sMNBDxd66z3kqnqFQO40OTsIhM-_8A&oe=6758FED8", "Available"));
        products.add(new Product("RDPRNTS 3", 299.00, "This collection brings the spirit and pride of being a RED SPARTAN at Batangas State University to life.", "https://scontent.fmnl13-4.fna.fbcdn.net/v/t39.30808-6/465713945_992271949594018_5605422225032390739_n.jpg?stp=dst-jpg_s600x600_tt6&_nc_cat=109&ccb=1-7&_nc_sid=127cfc&_nc_eui2=AeFqKd4WV8rqlxR1sKzsfcHFJbhdinvzUMoluF2Ke_NQyjB6EulFXqWTxXjtK9gCymSH_Q9AQDsOK3wpOlilgOkT&_nc_ohc=wV2QPmTBak0Q7kNvgGwD_Fr&_nc_zt=23&_nc_ht=scontent.fmnl13-4.fna&_nc_gid=A16gTib8NODn269nmZTk3Nm&oh=00_AYCxFB2TBdqBjFXTq3CHtU9gBBOcfmb7O5B8u2sgHnfhGg&oe=67590EBC", "Available"));
        products.add(new Product("RDPRNTS 4", 299.00, "This collection brings the spirit and pride of being a RED SPARTAN at Batangas State University to life.", "https://scontent.fmnl13-1.fna.fbcdn.net/v/t39.30808-6/465703352_992271936260686_874291989651737158_n.jpg?stp=dst-jpg_s600x600_tt6&_nc_cat=104&ccb=1-7&_nc_sid=127cfc&_nc_eui2=AeHprn37C1QZpUVRvSD-eXxUUucEHw5enxFS5wQfDl6fEX1P0FI7XXdfocCZpj_J4X7IS9sgXlcmJr70TUJ6Mxtx&_nc_ohc=-T--iddL9XgQ7kNvgEeTWlM&_nc_zt=23&_nc_ht=scontent.fmnl13-1.fna&_nc_gid=A16gTib8NODn269nmZTk3Nm&oh=00_AYCsaEGp5CmAb27BUvWBoyBxlKtim1uJ2PTNsgQDQam8nA&oe=675904E8", "Available"));

        ProductAdapter adapter = new ProductAdapter(products, this);
        productsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        productsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onProductClick(Product product) {
        if (mListener != null) {
            mListener.onProductSelected(product);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}