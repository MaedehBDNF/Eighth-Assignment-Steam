package Shared;

import Shared.Enums.Title;

public class Request {
    private Title title;
    private Object data;

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
