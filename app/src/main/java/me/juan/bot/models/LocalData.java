package me.juan.bot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class LocalData {

    public Asesor asesor;
    boolean isGranted;

    public LocalData(Asesor asesor) {
        this.asesor = asesor;
    }
}
