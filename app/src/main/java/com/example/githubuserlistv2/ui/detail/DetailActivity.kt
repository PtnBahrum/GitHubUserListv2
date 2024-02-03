package com.example.githubuserlistv2.ui.detail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuserlistv2.R
import com.example.githubuserlistv2.adapter.PagerAdapter
import com.example.githubuserlistv2.utils.ViewModelFactory
import com.example.githubuserlistv2.databinding.ActivityDetailBinding
import com.example.githubuserlistv2.model.User
import com.example.githubuserlistv2.ui.favorite.FavoriteViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel : DetailViewModel by viewModels()
    private lateinit var favoriteViewModel : FavoriteViewModel
    private lateinit var viewPager : ViewPager2
    private lateinit var tabs : TabLayout

    private var url : String? = ""
    private var check = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        favoriteViewModel = obtainViewModel(this@DetailActivity)

        detailViewModel.isLoading.observe(this) {
            showProgress(it)
        }
        detailViewModel.username = intent.getStringExtra("USERNAME")!!
        detailViewModel.setDetailUser()

        binding.btnShare.setOnClickListener(this)
        binding.btnVisit.setOnClickListener(this)

        setToolbar()
        setProfile()
        setViewPager()
        favoriteViewModel.getAllUser().observe(this){
            for(data in it) {
                if(data.login == detailViewModel.username) {
                    check = true
                    FavoriteButtomClick()
                }
            }

        }
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = detailViewModel.username
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun FavoriteButtomClick() {
        if(check) {
            binding.btnFavorite.setBackgroundColor(resources.getColor(R.color.gray_1))
            binding.btnFavorite.setIconResource(R.drawable.ic_unfavorite)
            binding.btnFavorite.text = "Unfavorite"
        } else {
            binding.btnFavorite.setBackgroundColor(resources.getColor(R.color.white))
            binding.btnFavorite.setIconResource(R.drawable.ic_favorite_border)
            binding.btnFavorite.text = "Favorite"
        }
    }
    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_share -> {
                val share: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, url)
                    type = "text/plain"
                }
                startActivity(Intent.createChooser(share, "Share to:"))
            }
            R.id.btn_visit -> {
                val url = url
                val action = Intent(Intent.ACTION_VIEW)
                action.data = Uri.parse(url)
                startActivity(action)
            }
        }
    }

    private fun setProfile() {
        detailViewModel.user.observe(this) {
            binding.apply {

                tvBio.isVisible = it.bio != null
                company.isVisible = it.company != null
                location.isVisible = it.location != null
                blog.isVisible = it.blog !=""

                it.apply {
                    tvName.text = name
                    tvFollower.text = followers.toString()
                    tvFollowing.text = following.toString()
                    tvRepository.text = public_repos.toString()
                    tvBio.text = bio
                    tvCompany.text = company
                    tvBlog.text = blog
                    tvLocation.text = location
                    url = html_url

                    Glide.with(this@DetailActivity)
                        .load(avatar_url)
                        .circleCrop()
                        .into(ivUser)

                    btnFavorite.setOnClickListener {
                        if(!check) {
                            favoriteViewModel.insert(User(
                                id = id,
                                login = login,
                                name = name,
                                followers_url = followers_url,
                                following_url = following_url,
                                followers = followers,
                                following  = following,
                                avatar_url = avatar_url,
                                repos_url = repos_url,
                                bio = bio,
                                company = company,
                                public_repos = public_repos,
                                url = url,
                                html_url= html_url,
                                blog = blog,
                                location = location,
                            ))
                            Toast.makeText(this@DetailActivity, "${detailViewModel.username} Add to Favorite", Toast.LENGTH_SHORT).show()
                            check = true
                        } else {
                            favoriteViewModel.delete(login!!)
                            Toast.makeText(this@DetailActivity, "${detailViewModel.username} remove from Favorite", Toast.LENGTH_SHORT).show()
                            check = false
                        }
                        FavoriteButtomClick()
                    }
                }
            }

        }
    }

    private fun setViewPager() {
        val pagerAdapter = PagerAdapter(this, detailViewModel.username)
        binding.apply {
            viewPager = viewpager
            tabs = tablayout
        }

        viewPager.adapter = pagerAdapter
        val titles = resources.getStringArray(R.array.tab_menu)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    private fun showProgress(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }
}