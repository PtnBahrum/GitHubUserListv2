package com.example.githubuserlistv2.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserlistv2.R
import com.example.githubuserlistv2.adapter.UserAdapter
import com.example.githubuserlistv2.databinding.FragmentHomeBinding
import com.example.githubuserlistv2.ui.search.SearchActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var userAdapter : UserAdapter
    private val viewModel : HomeViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)

        setHasOptionsMenu(true)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.inflateMenu(R.menu.topbar_menu_home)
        binding.rvUser.layoutManager = LinearLayoutManager(activity)

        viewModel.users.observe(viewLifecycleOwner) {
            userAdapter = UserAdapter(it)
            userAdapter.notifyDataSetChanged()
            binding.rvUser.adapter = userAdapter
            showProgress(false)
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            showProgress(it)
        }
        viewModel.setUser()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.topbar_menu_home, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search -> {
                startActivity(Intent(activity, SearchActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun showProgress(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}