package Logica;

import java.io.Serializable;

public class Usuario_Serie implements Serializable {
    private String user;
    private String name_serie;
    private boolean like,dislike;

    public Usuario_Serie(String user, String name_serie, boolean like, boolean dislike) {
        this.user = user;
        this.name_serie = name_serie;
        this.like = like;
        this.dislike = dislike;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName_serie() {
        return name_serie;
    }

    public void setName_serie(String name_serie) {
        this.name_serie = name_serie;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public boolean isDislike() {
        return dislike;
    }

    public void setDislike(boolean dislike) {
        this.dislike = dislike;
    }
}
