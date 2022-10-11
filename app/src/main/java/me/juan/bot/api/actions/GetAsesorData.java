package me.juan.bot.api.actions;

import me.juan.bot.api.Connection;
import me.juan.bot.api.Service;
import me.juan.bot.api.ServiceResponse;
import me.juan.bot.models.Asesor;

public class GetAsesorData extends Service<String, Asesor> {

    public GetAsesorData() {
        super("GetAsesorData");
    }

    @Override
    protected ServiceResponse<Asesor> handle(String input) {
        String values = String.format("{'codigo': '%s'}", input);
        Connection<Asesor> conector = new Connection<>("AsesorLogin", values, Asesor.class);
        return conector.getResponse();
    }
}