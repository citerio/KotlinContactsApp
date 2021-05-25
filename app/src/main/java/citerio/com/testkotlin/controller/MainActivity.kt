package citerio.com.testkotlin.controller

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import citerio.com.testkotlin.R
import citerio.com.testkotlin.model.Contact
import citerio.com.testkotlin.model.ContactAdapter
import citerio.com.testkotlin.model.Contact_
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import io.objectbox.query.Query
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    /*****************************variables*****************************/

    private var contacts_list_ : androidx.recyclerview.widget.RecyclerView? = null
    private var no_contacts_: TextView? = null
    private var toolbar_ : androidx.appcompat.widget.Toolbar? = null
    private var add_contact_button_ : FloatingActionButton? = null
    private var mLayoutManager: androidx.recyclerview.widget.RecyclerView.LayoutManager? = null
    private lateinit var contactBox: Box<Contact>
    private lateinit var contactQuery: Query<Contact>
    private var contactReceiver: ContactBroadCastReceiver? = null
    private val NEW_CONTACT_ADDED = "NEW_CONTACT_ADDED"
    private var adapter : ContactAdapter? = null
    private val TAG = "KotlinTestApp"

    /*****************************variables*****************************/

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar_ = this.toolbar
        setSupportActionBar(toolbar_)
        supportActionBar?.title = ""
        contacts_list_ = this.contacts_list
        no_contacts_ = this.no_contacts
        add_contact_button_ = this.add_contact_button
        contactReceiver = ContactBroadCastReceiver()

        mLayoutManager = androidx.recyclerview.widget.LinearLayoutManager(applicationContext)
        contacts_list_?.layoutManager = mLayoutManager

        add_contact_button_!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {

                val create_person = Intent(this@MainActivity, Create_Contact::class.java)
                startActivity(create_person)

            }
        }

        )

        contactBox = (application as App).boxStore.boxFor<Contact>()

        show_contacts()

    }

    /*****************************showing contacts from db*****************************/
    fun show_contacts(){

        object : AsyncTask<androidx.recyclerview.widget.RecyclerView, Void, ContactAdapter>() {


            private var v: androidx.recyclerview.widget.RecyclerView? = null

            override fun doInBackground(vararg params: androidx.recyclerview.widget.RecyclerView): ContactAdapter? {

                v = params[0]

                contactQuery = contactBox.query().order(Contact_.name).build()
                var contacts = contactQuery.find()

                adapter = ContactAdapter(this@MainActivity, contacts = contacts as ArrayList<Contact>)

                return adapter
            }

            override fun onPostExecute(contactadapter: ContactAdapter?) {
                super.onPostExecute(contactadapter)

                if (contactadapter != null) {

                    v!!.adapter = contactadapter

                    val items = adapter?.itemCount

                    if(!(items == null || items > 0)){
                        no_contacts_?.visibility = View.VISIBLE
                    }



                } else {

                    //v!!.visibility = View.GONE
                    no_contacts_?.visibility = View.VISIBLE
                }

            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, contacts_list_)


    }

    /*****************************receiving new contacts in db and updating contacts UI*****************************/
    private inner class ContactBroadCastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val local = intent.extras!!.getString("RESULT_CODE")!!
            when(local){
                NEW_CONTACT_ADDED -> updateContacts()
            }

        }
    }

    /*****************************updating contacts UI*****************************/
    fun updateContacts() {
        val contacts = contactQuery.find()
        adapter?.setContacts(contacts = contacts as ArrayList<Contact>)
        val items = adapter?.itemCount
        if(!(items == null || items <= 0)){
            no_contacts_?.visibility = View.INVISIBLE
        }
        Log.v(TAG, "update contacts called")
    }

    override fun onStart() {
        applicationContext.registerReceiver(contactReceiver, IntentFilter("citerio.com.testkotlin"))
        super.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
        applicationContext.unregisterReceiver(contactReceiver)
    }

    override fun onResume() {
        super.onResume()
        val child =  View.inflate(this, com.citerio.avengerslib.R.layout.avenger_float_button, null) as ConstraintLayout
        root.addView(child)
        val set = ConstraintSet()
        set.clone(root)
        set.connect(child.id, ConstraintSet.BOTTOM, root.id, ConstraintSet.BOTTOM)
        set.connect(child.id, ConstraintSet.TOP, root.id, ConstraintSet.TOP)
        set.connect(child.id, ConstraintSet.END, root.id, ConstraintSet.END)
        //set.connect(child.id, ConstraintSet.START, root.id, ConstraintSet.START)
        set.applyTo(root)


        child.findViewById<FloatingActionButton>(com.citerio.avengerslib.R.id.open_popup_button).setOnClickListener {
            com.citerio.avengerslib.Notifications.showPopUp(this@MainActivity)
        }
    }
}
