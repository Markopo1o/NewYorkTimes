package hu.jm.newyorktimes.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.android.material.snackbar.Snackbar
import hu.jm.newyorktimes.BuildConfig
import hu.jm.newyorktimes.check.CheckInternetConnection
import hu.jm.newyorktimes.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {
    private lateinit var url: String
    private var checkInternetConnection: CheckInternetConnection? = null
    private var connected = false
    private val args by navArgs<DetailsFragmentArgs>()
    private var _binding: FragmentDetailsBinding? = null
    private val binding
        get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onResume() {
        super.onResume()
        setView()
        checkInternetConnection()
        openInChrome()
    }
    private fun setView(){
        binding.ivPic.load(args.currentNews.pic)
        binding.tvDetailTitle.text = args.currentNews.title
        binding.tvDetailAuthor.text = args.currentNews.author
        binding.tvDetailUpdated.text = args.currentNews.updated
        url = args.currentNews.url
    }
    private fun openInChrome(){
        binding.btnOpenInChrome.setOnClickListener {
            if (connected){
                val intentChrome = Intent(Intent.ACTION_VIEW)
                intentChrome.setPackage(BuildConfig.CHROME_PACKAGE)
                intentChrome.data = Uri.parse(args.currentNews.url)
                startActivity(intentChrome)
            }else{
                showSnackbar("No internet connection, while your state is offline you can't open the news is Chrome")
            }
        }
    }
    private fun checkInternetConnection(){
        checkInternetConnection = CheckInternetConnection(requireActivity().application)
        checkInternetConnection?.observe(this) { isConnected ->
            if (isConnected) {
                //binding.btnOpenInChrome.isEnabled = true
                //binding.tvCheckInternet.isVisible = false
                connected = true
            } else {
                showSnackbar("No internet connection, while your state is offline you can't open the news is Chrome")
                //binding.btnOpenInChrome.isEnabled = false
                //binding.tvCheckInternet.isVisible = true
                //binding.tvCheckInternet.text = getString(R.string.noInternet)
                connected = false
            }
        }
    }
    private fun showSnackbar(string: String){
        Snackbar.make(binding.detailsFragment,string, Snackbar.LENGTH_INDEFINITE)
            .setAction("Close", View.OnClickListener {

            }).show()
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        checkInternetConnection = null
    }
}