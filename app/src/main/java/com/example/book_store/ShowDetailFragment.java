package com.example.book_store;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.book_store.database.CartDao;
import com.example.book_store.model.Book;
import com.example.book_store.model.CartItem;
import com.example.book_store.ui.FormatCurrency;

import java.text.DecimalFormat;

public class ShowDetailFragment extends Fragment {
    TextView txtTitle,txtPrice,txtDes,txtAuthor,txtYear,txtCate,txtNum;
    ImageView img;
    Button btnGiam,btnTang,btnAddToCart,btnCheckOut;
    Book book;
    int numOfBook;
    CartDao cartDao;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_detail, container, false);
        txtTitle = (TextView) view.findViewById(R.id.titleTxt);
        txtPrice = (TextView) view.findViewById(R.id.priceTxt);
        img = (ImageView) view.findViewById(R.id.detail_image);
        txtDes = (TextView) view.findViewById(R.id.descriptionTxt);
        txtAuthor = (TextView) view.findViewById(R.id.detail_author);
        txtYear = (TextView) view.findViewById(R.id.detail_year);
        txtCate = (TextView) view.findViewById(R.id.detail_category);
        btnGiam = (Button) view.findViewById(R.id.btngiamsoluong);
        btnTang = (Button) view.findViewById(R.id.btntangsoloung);
        btnAddToCart = (Button) view.findViewById(R.id.detail_btnAddToCart);
        btnCheckOut = (Button) view.findViewById(R.id.cart_btnCheckOut);
        txtNum = (TextView)view.findViewById(R.id.txtsoluong);
        numOfBook = 1;
        Bundle bundle = getArguments();
        if(bundle != null){
            book = bundle.getParcelable("book-target");
            fillData();
        }
        cartDao = new CartDao(getContext());
        //handle event;
        //Tang giam so luong
        handleEventNumOfBook();
        handleAddToCart();
        //Them vao gio hang
        return view;
    }
    private void handleAddToCart(){
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookId = book.getId();
                if(bookId != null){
                    CartItem cartItem = new CartItem();
                    cartItem.setBookId(bookId);
                    cartItem.setNum(numOfBook);
                    cartDao.addToCart(cartItem);
                    changeToCartFragment();
                }
            }
        });

    }
    private void changeToCartFragment(){
        CartFragment cartFragment = new CartFragment();
        this.getParentFragmentManager().beginTransaction().replace(R.id.container,cartFragment)
                .addToBackStack(null)
                .commit();
    }
    private void handleEventNumOfBook(){
        btnTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numOfBook += 1;
                setNum();
            }
        });
        btnGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numOfBook > 1) {
                    numOfBook -= 1;
                    setNum();
                }

            }
        });
    }
    private void fillData(){
        txtTitle.setText(book.getTitle());
        txtPrice.setText(FormatCurrency.formatVND(book.getPrice()));
        Glide.with(getContext()).load(book.getImgURL()).into(img);
        txtDes.setText(book.getDescription());
        txtAuthor.setText(book.getAuthor());
        txtYear.setText(Integer.toString(book.getYear()));
        txtCate.setText(book.getCategory());
        setNum();
    }
    private void setNum(){
        txtNum.setText(Integer.toString(numOfBook));
    }
}