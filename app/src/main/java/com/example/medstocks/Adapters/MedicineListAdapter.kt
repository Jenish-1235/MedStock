package com.example.medstocks.Adapters

import android.content.ClipData.Item
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.medstocks.Models.Medicine
import com.example.medstocks.R
import org.w3c.dom.Text

class MedicineListAdapter(context: Context, medicineList: ArrayList<Medicine>): RecyclerView.Adapter<MedicineListAdapter.MedicineViewHolder>() {
    var medicineList = medicineList
    var context = context

    class MedicineViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val medicineNameView:TextView = itemView.findViewById(R.id.medicineNameView)
        val companyNameView: TextView = itemView.findViewById(R.id.companyName)
        val quantityView:TextView = itemView.findViewById(R.id.quantityView)
        val expiryDateView:TextView = itemView.findViewById(R.id.expiryDateView)
        val sellButton: Button = itemView.findViewById(R.id.sellButton)
        val purchaseButton:Button = itemView.findViewById(R.id.purchaseButton)


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MedicineListAdapter.MedicineViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_medicine_list,parent, false)
        return MedicineViewHolder(itemView)
    }



    override fun onBindViewHolder(holder: MedicineListAdapter.MedicineViewHolder, position: Int) {
        val currentMedicine = medicineList[position]
        holder.quantityView.text = "Quantity: " + currentMedicine.quantity.toString()
        holder.companyNameView.text = currentMedicine.companyName
        holder.expiryDateView.text = "Expiry: " + currentMedicine.expiryDate
        holder.medicineNameView.text = currentMedicine.name
        holder.sellButton.setOnClickListener{
            Toast.makeText(context, "Sell Pressed", Toast.LENGTH_LONG).show()
        }

        holder.purchaseButton.setOnClickListener{
            Toast.makeText(context, "Purchase Pressed", Toast.LENGTH_LONG).show()
        }

    }

    override fun getItemCount(): Int {
        return medicineList.size
    }
}