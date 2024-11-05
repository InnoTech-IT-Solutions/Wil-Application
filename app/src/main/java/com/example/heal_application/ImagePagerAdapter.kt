import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.heal_application.R

class ImagePagerAdapter(
    private val images: List<Int>,       // List of image resource IDs
    private val titles: List<String>,     // List of titles
    private val descriptions: List<String> // List of descriptions
) : RecyclerView.Adapter<ImagePagerAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val descriptionTextView: TextView = view.findViewById(R.id.descriptionTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageView.setImageResource(images[position])
        holder.titleTextView.text = titles[position]               // Set the title
        holder.descriptionTextView.text = descriptions[position]    // Set the description
    }

    override fun getItemCount(): Int = images.size
}
