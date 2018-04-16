package citerio.com.testkotlin.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by Jose Ricardo on 13/04/2018.
 */
@Entity
data class Contact constructor(@Id var id: Long = 0, var name: String = "", var email: String = "", var age: Int = 0, var address: String = "", var phone: String = ""){

    /*var id: Int = 0;
    var name: String? = ""
    var email: String? = ""
    var age: Int? = 0
    var address: String? = ""
    var phone: String? = ""*/

    /*init {
        this.name = name
        this.email = email
        this.age = age
        this.address = address
        this.phone = phone
    }*/

}