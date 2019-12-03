package ec.com.store;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import ec.com.store.dto.CarritoListDto;
import ec.com.store.dto.ProductoDto;
import ec.com.store.models.Categoria;
import ec.com.store.models.Producto;
import ec.com.store.repositories.CategoriaRepository;
import ec.com.store.repositories.ProductoRepository;
import ec.com.store.services.ProductoService;

@RunWith(SpringRunner.class)
public class ProductoServiceTest {

	@Autowired
	private ProductoService productoService;
	@MockBean
	private ProductoRepository productoRepository;
	@MockBean
	private CategoriaRepository categoriaRepository;
	@Mock
    private Producto product;
	
	@TestConfiguration
    static class ProductoServiceImplTestContextConfiguration {
        @Bean
        public ProductoService employeeService() {
            return new ProductoService();
        }
    }
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		productoService = new ProductoService(productoRepository, categoriaRepository);
		Mockito.when(categoriaRepository.findByCodigo(findCategoria().getCodigo())).thenReturn(findCategoria());
	}

	private Categoria findCategoria() {
		Categoria categoria = new Categoria();
		categoria.setCodigo("1");
		categoria.setDescripcion("Camisetas");
		return categoria;
	}
	
	private Producto createProducto() {
		Producto producto = new Producto();
		producto.setId(1);
		producto.setNombre("Camiseta Polo Spiderman");
		producto.setDescripcion("Camiseta Polo Spiderman");
		producto.setCantidad(10);
		producto.setPrecio(new BigDecimal(10));
		producto.setUrl("url");
		producto.setCategoria(findCategoria());
		return producto;
	}
	
	private Producto findProducto() {
		Producto producto = new Producto();
		producto.setId(2);
		producto.setNombre("Camiseta Polo Spiderman");
		producto.setDescripcion("Camiseta Polo Spidermantalla M");
		producto.setCantidad(10);
		producto.setPrecio(new BigDecimal(10));
		producto.setUrl("spiderman.jpg");
		return producto;
	}
	
	
	@Test
	public void whenValidName_thenProductShouldBeFound() {
		String name = "Supereroes two";
		product = createProducto();
		Producto producto = findProducto();
		when(productoRepository.findByNombre(producto.getNombre())).thenReturn(producto);
		when(productoRepository.save(product)).thenReturn(product);
		ProductoDto savedProduct = productoService.crearProducto(product);
		// Assert
		assertThat(savedProduct.getNombre(), is(equalTo(name)));
	}
	
	
	@Test
	public void whenValidName_thenProductShouldNotFound() {
		product = createProducto();
		when(productoRepository.findByNombre(product.getNombre())).thenReturn(product);
		when(productoRepository.save(product)).thenReturn(product);
		ProductoDto savedProduct = productoService.crearProducto(product);
		// Assert
		assertThat(savedProduct, is(equalTo(null)));
	}
	
	@Test
	public void whenValidList_thenProductShouldBeFound() {
		List<Producto> lista = new ArrayList<>();
		lista.add(createProducto());
		when(productoRepository.findAll()).thenReturn(lista);
		CarritoListDto<ProductoDto> getProducts = productoService.obtenerProductos();
		assertThat(getProducts.getData().size(), is(equalTo(1)));
	}
	
}
