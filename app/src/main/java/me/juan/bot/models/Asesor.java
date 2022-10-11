package me.juan.bot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class Asesor {
    private int id;
    private String nombre;
    private String apellido;
    private String codigo;
    private String marcas;
    private int cantidadRegistros;
}
