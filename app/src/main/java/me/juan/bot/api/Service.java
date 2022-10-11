package me.juan.bot.api;

import android.util.Log;

import java.util.Date;

public abstract class Service<TIn, TOut> {

    private final String name;

    public Service(String name) {
        this.name = name;
    }

    protected abstract ServiceResponse<TOut> handle(TIn input);

    public ServiceResponse<TOut> execute(TIn input) {
        Log.i("Service", "Executing service " + name);
        Date start = new Date();
        ServiceResponse<TOut> result = handle(input);
        Date end = new Date();
        Log.i("Service", "Service " + name + " executed in " + (end.getTime() - start.getTime()) + "ms");
        return result;
    }




}
