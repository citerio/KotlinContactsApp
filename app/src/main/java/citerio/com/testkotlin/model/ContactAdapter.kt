package citerio.com.testkotlin.model

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


import java.util.ArrayList

import citerio.com.testkotlin.R

/**
 * Created by Jose Ricardo on 17/09/2016.
 */
class ContactAdapter(private val nContext: Context, contacts: ArrayList<Contact>) : androidx.recyclerview.widget.RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    private var contacts: ArrayList<Contact>


    init {
        this.contacts = ArrayList()
        this.contacts.addAll(contacts)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(nContext).inflate(R.layout.contact, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindMail(contacts[position])

    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    /*public void remove(int position) {
        notifications.remove(position);
        notifyItemRemoved(position);

    }*/


    override fun getItemId(position: Int): Long {

        notifyDataSetChanged()
        return super.getItemId(position)

    }

    fun setContacts(contacts: ArrayList<Contact>) {
        this.contacts = contacts
        notifyDataSetChanged()
    }

    /*public void swap(int firstPosition, int secondPosition){
        Collections.swap(notifications, firstPosition, secondPosition);
        notifyItemMoved(firstPosition, secondPosition);
    }*/


    inner class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {

        val name: TextView
        val email: TextView
        val phone: TextView


        init {

            name = view.findViewById<View>(R.id.name) as TextView
            email = view.findViewById<View>(R.id.email) as TextView
            phone = view.findViewById<View>(R.id.phone) as TextView

            /*view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent mail_details = new Intent(v.getContext(), MailDetails.class);
                    mail_details.putExtra("avatar", avatar.getText().toString());
                    mail_details.putExtra("id", id.getText().toString());
                    mail_details.putExtra("from", from.getText().toString());
                    mail_details.putExtra("message", message.getText().toString());
                    mail_details.putExtra("date", date.getText().toString());
                    mail_details.putExtra("time", time.getText().toString());
                    mail_details.putExtra("position", getAdapterPosition());
                    mail_details.putExtra("confirmed", (Boolean) confirmed.getTag());

                    mail_details.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    v.getContext().startActivity(mail_details);

                }
            });*/

        }

        fun bindMail(contact: Contact) {

            this.name.text = contact.name.capitalize()
            this.email.text = contact.email.decapitalize()
            this.phone.text = contact.phone
        }


    }


}
