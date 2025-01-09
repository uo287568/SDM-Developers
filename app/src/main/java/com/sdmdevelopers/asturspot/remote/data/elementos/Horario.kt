package com.sdmdevelopers.asturspot.remote.data.elementos

import com.google.gson.annotations.SerializedName

data class Horario(
    @SerializedName("sábado")
    val Saturday: List<String>,
    @SerializedName("domingo")
    val Sunday: List<String>,
    @SerializedName("lunes")
    val Monday: List<String>,
    @SerializedName("martes")
    val Tuesday: List<String>,
    @SerializedName("miércoles")
    val Wednesday: List<String>,
    @SerializedName("jueves")
    val Thursday: List<String>,
    @SerializedName("viernes")
    val Friday: List<String>
) {

    override fun toString() :String{
        var sb = StringBuilder()
        if(Monday != null){
            sb.append(("Lunes:\t%s\n").format(Monday.toString()))
        }
        if(Tuesday != null){
            sb.append(("Martes:\t%s\n").format(Tuesday.toString()))
        }
        if(Wednesday != null){
            sb.append(("Miércoles:\t%s\n").format(Wednesday.toString()))
        }
        if(Thursday != null){
            sb.append(("Jueves:\t%s\n").format(Thursday.toString()))
        }
        if(Friday != null){
            sb.append(("Viernes:\t%s\n").format(Friday.toString()))
        }
        if(Saturday != null){
            sb.append(("Sábado:\t%s\n").format(Saturday.toString()))
        }
        if(Sunday != null){
            sb.append(("Domingo:\t%s\n").format(Sunday.toString()))
        }
        if(sb.toString() == ""){
            return "[Horario no disponible]"
        }
        else{
            return sb.toString()
        }
    }
}