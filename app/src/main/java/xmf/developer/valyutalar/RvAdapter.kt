package xmf.developer.valyutalar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import xmf.developer.valyutalar.databinding.ItemRvBinding
import xmf.developer.valyutalar.models.MyMoney

class RvAdapter(val  list:List<MyMoney>): RecyclerView.Adapter<RvAdapter.Vh>()  {

    inner class Vh(var itemBinding: ItemRvBinding): RecyclerView.ViewHolder(itemBinding.root){

        fun onBind(myMoney: MyMoney, position:Int){
            itemBinding.name.text = myMoney.CcyNm_EN
            itemBinding.rate.text = myMoney.Rate
            itemBinding.diff.text = myMoney.Diff
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size
}