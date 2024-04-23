package com.example.settleinn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.settleinn.databinding.FragmentSavedHousesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SavedHousesFragment : Fragment() {

    private lateinit var binding: FragmentSavedHousesBinding
    private lateinit var viewModel: SavedHouseViewModel
    private lateinit var adapter: HouseListAdapter
    private lateinit var houseDao: HouseDao
    private lateinit var savedHouseViewModel: SavedHouseViewModel
    private lateinit var houses: List<House>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedHousesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel = ViewModelProvider(this).get(SavedHouseViewModel::class.java)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val database = AppDatabase.getDatabase(view.context)
        houseDao = database.houseDao()
        savedHouseViewModel = SavedHouseViewModel(houseDao)

        savedHouseViewModel.viewModelScope.launch(Dispatchers.IO) {
            houses = savedHouseViewModel.getAllSavedHouses()
            //Log.v("degub", entries[0].date)
            adapter = HouseListAdapter(mutableListOf()) { house ->
                // Implement saving logic here
                // For example, you can insert the house into your database
                // You can access your DAO through your ViewModel
                savedHouseViewModel.deleteHouse(house)
            }
            withContext(Dispatchers.Main) {
                // Update UI on the main thread
                adapter.updateData(houses.toMutableList())
                binding.recyclerView.adapter = adapter
                // adapter = EntryAdapter(entries, this@MainActivity)
            }
        }

//        viewModel.getAllSavedHouses().observe(viewLifecycleOwner) { savedHouses ->
//
//            adapter.updateData(savedHouses.toMutableList())
//
////            adapter.submitList(savedHouses)
//        }
    }
}
