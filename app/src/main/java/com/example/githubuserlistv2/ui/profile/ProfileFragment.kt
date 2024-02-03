package com.example.githubuserlistv2.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuserlistv2.R
import com.example.githubuserlistv2.databinding.FragmentProfileBinding
import com.example.githubuserlistv2.ui.detail.DetailViewModel
import com.example.githubuserlistv2.ui.settings.SettingsActivity
import com.google.android.material.tabs.TabLayout

class ProfileFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DetailViewModel
    private lateinit var viewPager : ViewPager2
    private lateinit var tabs : TabLayout

    private var url_share : String? = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        setHasOptionsMenu(true)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.inflateMenu(R.menu.topbar_menu_profile)
        viewModel.username = "PtnBahrum"
        binding.toolbar.title = viewModel.username

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showProgress(it)
        }
        viewModel.setDetailUser()
        setProfile()

        binding.btnShare.setOnClickListener(this)
        binding.btnVisit.setOnClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.topbar_menu_profile, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings -> {
                startActivity(Intent(activity, SettingsActivity::class.java))
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun setProfile() {
        viewModel.user.observe(viewLifecycleOwner) {
            binding.apply {

                company.isVisible = it.company != null
                location.isVisible = it.location != null
                tvBio.isVisible = it.bio != null
                blog.isVisible = it.blog != null

                it.apply {
                    tvName.text = name
                    tvFollower.text = followers.toString()
                    tvFollowing.text = following.toString()
                    tvRepository.text = public_repos.toString()
                    tvBio.text = bio
                    tvBlog.text = blog
                    tvCompany.text = company
                    tvLocation.text = location
                    url_share = html_url

                    Glide.with(requireActivity())
                        .load(avatar_url)
                        .circleCrop()
                        .into(ivUser)
                }
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id){
            R.id.btn_visit ->{
                val url = url_share
                val action = Intent(Intent.ACTION_VIEW)
                action.data = Uri.parse(url)
                startActivity(action)
            }
            R.id.btn_share ->{
                val share: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, url_share)
                    type = "text/plain"
                }
                startActivity(Intent.createChooser(share, "Share to:"))
            }
        }
    }
    private fun showProgress(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}