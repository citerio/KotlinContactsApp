package citerio.com.testkotlin.controller

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.textfield.TextInputLayout
import android.widget.Button
import android.widget.EditText
import citerio.com.testkotlin.R
import kotlinx.android.synthetic.main.create_contact.*
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import citerio.com.testkotlin.model.Contact
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import android.widget.Toast
import org.json.JSONException
import android.content.Intent




class Create_Contact : AppCompatActivity() {

    /*****************************variables*****************************/

    private var name_ : EditText? = null
    private var email_ : EditText? = null
    private var age_ : EditText? = null
    private var phone_ : EditText? = null
    private var address_ : EditText? = null
    private var name_layout_ : TextInputLayout? = null
    private var email_layout_ : TextInputLayout? = null
    private var age_layout_ : TextInputLayout? = null
    private var phone_layout_ : TextInputLayout? = null
    private var address_layout_ : TextInputLayout? = null
    private var cancel_button_ : Button? = null
    private var add_button_ : Button? = null
    private var toolbar_ : androidx.appcompat.widget.Toolbar? = null
    private lateinit var contactBox: Box<Contact>
    private val TAG = "KotlinTestApp"
    private val ACTION = "citerio.com.testkotlin"
    private val intent_ma = Intent(ACTION)

    /*****************************variables*****************************/

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_contact)

        toolbar_ = this.toolbar
        setSupportActionBar(toolbar_)
        supportActionBar?.title = ""
        name_ = this.name
        email_ = this.email
        age_ =  this.age
        phone_ = this.phone
        address_ = this.address
        name_layout_ = this.name_layout
        email_layout_ = this.email_layout
        age_layout_ = this.age_layout
        phone_layout_ = this.phone_layout
        address_layout_ = this.address_layout
        cancel_button_ = this.cancel_button
        add_button_ = this.add_button

        name_?.addTextChangedListener(MyTextWatcher(this.name))
        age_?.addTextChangedListener(MyTextWatcher(this.age))
        email_?.addTextChangedListener(MyTextWatcher(this.email))
        phone_?.addTextChangedListener(MyTextWatcher(this.phone))
        address_?.addTextChangedListener(MyTextWatcher(this.address))

        contactBox = (application as App).boxStore.boxFor<Contact>()


        add_button_!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {

                if(!name_?.text.toString().trim().equals("") && !age_?.text.toString().trim().equals("") && !email_?.text.toString().trim().equals("") && !address_?.text.toString().trim().equals("") && !phone_?.text.toString().trim().equals("")){

                    val c_name = name_?.text.toString().trim()
                    val c_age = age_?.text.toString().trim()
                    val c_email = email_?.text.toString().trim()
                    val c_address = address_?.text.toString().trim()
                    val c_phone = phone_?.text.toString().trim()

                    addContact(c_name, c_email, c_age, c_address, c_phone)

                }else{

                    Toast.makeText(this@Create_Contact, "Please, fill out all fields", Toast.LENGTH_LONG).show()
                }
            }
        }

        )

        cancel_button_!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {

                finish()
            }
        }

        )

    }

    /*****************************adding a new contact to DB*****************************/
    fun addContact(name: String, email: String, age: String, address: String, phone: String){


        object : AsyncTask<String, Void, String>() {


            private var message = ""


            override fun doInBackground(vararg params: String): String {

                try {

                    val c_name = params[0]
                    val c_age =  params[1].toInt()
                    val c_email = params[2]
                    val c_address = params[3]
                    val c_phone = params[4]

                    val contact  = Contact(name = c_name, email = c_email, age = c_age, address = c_address, phone = c_phone )

                    contactBox.put(contact)

                    message = "success";
                    return message;



                } catch (e: Exception) {

                    e.printStackTrace()
                    message = "failure";
                    return message;

                }
            }

            override fun onPostExecute(m: String) {
                super.onPostExecute(m)

                if (m.equals("success")) {

                    try {

                        Log.v(TAG, "New contact inserted in DB")
                        intent_ma.putExtra("RESULT_CODE", "NEW_CONTACT_ADDED")
                        applicationContext.sendBroadcast(intent_ma)
                        Toast.makeText(this@Create_Contact, "New contact added", Toast.LENGTH_LONG).show()
                        finish()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                } else {

                    Log.v(TAG, "Error on inserting new contact in DB")
                    Toast.makeText(this@Create_Contact, "ERROR inside adding contact", Toast.LENGTH_LONG).show()

                }

            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, name, age, email, address, phone)


    }

    /*****************************validating data entries*****************************/
    private inner class MyTextWatcher(private val view: View) : TextWatcher {

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable) {

            when (view.id) {
                R.id.name -> validate_name()
                R.id.age -> validate_age()
                R.id.email -> validate_email()
                R.id.phone -> validate_phone()
                R.id.address -> validate_address()
            }

        }
    }

    private fun validate_name(): Boolean {

        if (name.text.toString().isEmpty()) {

            name_layout_?.error = getString(R.string.error_empty_field)

            return false

        } else {

            name_layout_?.isErrorEnabled = false

        }

        return true

    }

    private fun validate_email(): Boolean {

        if (email.text.toString().isEmpty()) {

            email_layout_?.error = getString(R.string.error_empty_field)

            return false

        } else {

            email_layout_?.isErrorEnabled = false

        }

        return true

    }

    private fun validate_age(): Boolean {

        if (age.text.toString().isEmpty()) {

            age_layout_?.error = getString(R.string.error_empty_field)

            return false

        } else {

            age_layout_?.isErrorEnabled = false

        }

        return true

    }

    private fun validate_phone(): Boolean {

        if (phone.text.toString().isEmpty()) {

            phone_layout_?.error = getString(R.string.error_empty_field)

            return false

        } else {

            phone_layout_?.isErrorEnabled = false

        }

        return true

    }

    private fun validate_address(): Boolean {

        if (address.text.toString().isEmpty()) {

            address_layout_?.error = getString(R.string.error_empty_field)

            return false

        } else {

            address_layout_?.isErrorEnabled = false

        }

        return true

    }
}
