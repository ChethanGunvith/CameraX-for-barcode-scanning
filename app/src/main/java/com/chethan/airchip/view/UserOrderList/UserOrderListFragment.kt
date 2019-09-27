package com.chethan.airchip.view.ScanTheQRCode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.chethan.airchip.AppExecutors
import com.chethan.airchip.R
import com.chethan.airchip.binding.FragmentDataBindingComponent
import com.chethan.airchip.common.ProductsListAdapter
import com.chethan.airchip.databinding.UserOrderFragmentBinding
import com.chethan.airchip.di.Injectable
import com.chethan.airchip.model.OrderPayload
import com.chethan.airchip.testing.OpenForTesting
import com.chethan.airchip.utils.autoCleared
import com.google.gson.Gson
import javax.inject.Inject

/**
 * Created by Chethan on 9/17/2019.
 */

@OpenForTesting
class UserOrderListFragment : Fragment(), Injectable {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var userOrderListViewModel: UserOrderListViewModel

    @Inject
    lateinit var appExecutors: AppExecutors

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<UserOrderFragmentBinding>()

    private var adapter by autoCleared<ProductsListAdapter>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.user_order_fragment,
            container,
            false,
            dataBindingComponent
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        userOrderListViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(UserOrderListViewModel::class.java)
        binding.setLifecycleOwner(viewLifecycleOwner)
        val params = UserOrderListFragmentArgs.fromBundle(arguments!!)
        val orderPayload = Gson().fromJson(params.userOrderList, OrderPayload::class.java)

        orderPayload.cons?.let { userOrderListViewModel.setProductIds(it) }

        initRecyclerView()
        val rvAdapter = ProductsListAdapter(
            dataBindingComponent = dataBindingComponent,
            appExecutors = appExecutors
        )

        binding.postList.adapter = rvAdapter
        adapter = rvAdapter

    }

    private fun initRecyclerView() {
        binding.productList = userOrderListViewModel.products
        userOrderListViewModel.products.observe(viewLifecycleOwner, Observer { result ->
            if (result.data != null)
                adapter.submitList(result?.data)
        })


    }


    fun navController() = findNavController()

}