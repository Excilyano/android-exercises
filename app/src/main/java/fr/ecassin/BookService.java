package fr.ecassin;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface BookService {

    @GET("books")
    Call<List<Book>> getBooks();
}
