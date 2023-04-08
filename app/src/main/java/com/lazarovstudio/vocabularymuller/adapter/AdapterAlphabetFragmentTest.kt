//
//
//import android.graphics.Color
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.AsyncListDiffer
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.RecyclerView
//import com.lazarovstudio.vocabularymuller.databinding.ListAlphabetBinding
//import com.lazarovstudio.vocabularymuller.fragments.alphabetFragment.AlphabetFragment
//import com.lazarovstudio.vocabularymuller.model.Dictionary
//
//private val listWordFavorite = ArrayList<Dictionary>()
//
//class AdapterAlphabetFragmentTest(private val wordCardTest: AlphabetFragment) :
//    RecyclerView.Adapter<AdapterAlphabetFragmentTest.VHolder>() {
//
//    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Dictionary>() {
//
//        override fun areItemsTheSame(oldItem: Dictionary, newItem: Dictionary): Boolean {
//            return oldItem.id === newItem.id
//        }
//
//        override fun areContentsTheSame(oldItem: Dictionary, newItem: Dictionary): Boolean {
//            return oldItem == newItem
//        }
//
//    }
//    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)
//
//    class VHolder(private val binding: ListAlphabetBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        fun setData(
//            item: Dictionary,
//            wordCard: AlphabetFragment,
//        ) = with(binding) {
//            binding.word.text = item.word
//            binding.descriptions.text = item.description
//            binding.countViewed.text = item.countSee
//
//            if (item.save) {
//                binding.saveFavorite.setColorFilter(Color.GREEN)
//            }
//
//            binding.card.setOnClickListener {
//                wordCard.showDetailFragment(item)
//            }
//            binding.saveFavorite.setOnClickListener {
//                listWordFavorite.add(item)
//                wordCard.saveFavoriteWord(listWordFavorite)
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder{
//        return VHolder(
//            ListAlphabetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        )
//    }
//
//    override fun onBindViewHolder(holder: VHolder, position: Int) {
//        holder.setData(differ.currentList[position], wordCardTest)
//    }
//
//    override fun getItemCount(): Int {
//        return differ.currentList.size
//    }
//
//    fun submitList(list: List<Dictionary>) {
//        differ.submitList(list)
//    }
//}
//
