package com.hexademical.awayfromusapplication.ResourceRecyclerView

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hexademical.awayfromusapplication.API.UserResponse
import com.hexademical.awayfromusapplication.R

class ResourceAdapter(private val resourceList: ArrayList<UserResponse.Resource>) : RecyclerView.Adapter<ResourceAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_resource, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ResourceAdapter.ViewHolder, position: Int) {
        val currentResource = resourceList[position]
        holder._resource_name.text = currentResource.getName()

        var resourceStatus = "NO IP SYNC"
        holder._resource_status.setTextColor(Color.parseColor("#FFBB54"))
        if(currentResource.getStatus() != null && currentResource.getStatus() != ""){
            resourceStatus = currentResource.getStatus()!!
            holder._resource_status.setTextColor(Color.parseColor("#8DF291"))
        }
        holder._resource_status.text = resourceStatus

        var resourcePlan : String
        if(currentResource.getDayLeft() == -1) {
            resourcePlan = "PERMANENTLY"
            holder._resource_plan.setTextColor(Color.parseColor("#AFAFAF"))
        }else if(currentResource.getDayLeft() == 0){
            resourcePlan = "EXPIRED"
            holder._resource_plan.setTextColor(Color.parseColor("#FF5555"))
        }else{
            resourcePlan = "EXPIRE IN ${currentResource.getDayLeft()} DAY"
            holder._resource_plan.setTextColor(Color.parseColor("#5AA0E2"))
        }
        holder._resource_plan.text = resourcePlan
    }

    override fun getItemCount(): Int {
        return resourceList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val _resource_name : TextView = itemView.findViewById(R.id.resource_name)
        val _resource_status : TextView = itemView.findViewById(R.id.resource_status)
        val _resource_plan : TextView = itemView.findViewById(R.id.resource_plan)
    }
}