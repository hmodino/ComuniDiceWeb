package com.comunidice.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.comunidice.web.model.Errors;
import com.comunidice.web.model.PagoServiceMock;
import com.comunidice.web.model.ShoppingCart;
import com.comunidice.web.model.ShoppingCartLine;
import com.comunidice.web.util.Actions;
import com.comunidice.web.util.AttributeNames;
import com.comunidice.web.util.ConfigurationManager;
import com.comunidice.web.util.ConfigurationParameterNames;
import com.comunidice.web.util.ControllerPaths;
import com.comunidice.web.util.ErrorCodes;
import com.comunidice.web.util.FileUtils;
import com.comunidice.web.util.ParameterNames;
import com.comunidice.web.util.ParameterUtils;
import com.comunidice.web.util.RedirectOrForward;
import com.comunidice.web.util.SelectClass;
import com.comunidice.web.util.SessionManager;
import com.comunidice.web.util.SetAttribute;
import com.comunidice.web.util.ValidationUtils;
import com.comunidice.web.util.ViewPaths;
import com.comunidice.web.util.WebConstants;
import com.comunidice.web.util.WebUtils;
import com.rollanddice.comunidice.model.Comentario;
import com.rollanddice.comunidice.model.Compra;
import com.rollanddice.comunidice.model.Criteria;
import com.rollanddice.comunidice.model.Favorito;
import com.rollanddice.comunidice.model.Juego;
import com.rollanddice.comunidice.model.LineaCompra;
import com.rollanddice.comunidice.model.Producto;
import com.rollanddice.comunidice.model.Results;
import com.rollanddice.comunidice.model.Usuario;
import com.rollanddice.comunidice.service.impl.ComentarioServiceImpl;
import com.rollanddice.comunidice.service.impl.CompraServiceImpl;
import com.rollanddice.comunidice.service.impl.FavoritoServiceImpl;
import com.rollanddice.comunidice.service.impl.ProductoServiceImpl;
import com.rollanddice.comunidice.service.spi.ComentarioService;
import com.rollanddice.comunidice.service.spi.CompraService;
import com.rollanddice.comunidice.service.spi.FavoritoService;
import com.rollanddice.comunidice.service.spi.ProductoService;

@WebServlet("/producto")
public class ProductoServlet extends HttpServlet {

	private static int count = Integer.valueOf(
			ConfigurationManager.getInstance().getParameter(
					ConfigurationParameterNames.RESULTS_PAGE_SIZE_DEFAULT)); 

	private static int pagingPageCount = Integer.valueOf(
			ConfigurationManager.getInstance().getParameter(
					ConfigurationParameterNames.RESULTS_PAGING_PAGE_COUNT)); 

	private static Logger logger = LogManager.getLogger(ProductoServlet.class);
	private static ProductoService service = null;
	private static ComentarioService commentService = null;
	private static FavoritoService favouriteService = null;
	private static CompraService buyService = null;

	public ProductoServlet() {
		super();
		service = new ProductoServiceImpl();
		commentService = new ComentarioServiceImpl();
		favouriteService = new FavoritoServiceImpl();
		buyService = new CompraServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Errors errors = new Errors();
		Producto p = null;
		Juego g = null;
		Criteria c = null;
		Comentario comment = null;
		Usuario u = null;
		Favorito f = null;
		ShoppingCart cart = null;
		ShoppingCartLine cartLine = null;
		Compra buy = null;
		LineaCompra buyLine = null;

		Results<Producto> products = null;
		Results<Juego> games = null;
		List<Juego> gs = null;
		List<Producto> ps = null;
		List<ShoppingCartLine> cartLines = null;
		List<LineaCompra> buyLines = null;
		List<Results> results = null;

		String action = ValidationUtils.parameterIsEmpty(request, ParameterNames.ACTION);

		Integer id = null;
		String language = null;
		Integer categoryId = null;
		String name = null;
		Double minPrice = null;
		Double maxPrice = null;
		Date minDate = null;
		Date maxDate = null;
		Integer favs = null;
		Integer rating = null;
		Integer sellerId = null;
		Integer sellerType = null;
		Integer minPublicationYear = null;
		Integer maxPublicationYear = null;
		Integer format = null;
		Integer coverType = null;
		Integer page = null;
		Integer startIndex = null;
		Integer totalPages = null;
		Integer firstPagedPage = null;
		Integer lastPagedPage = null;
		String content = null;
		Integer userId = null;
		Double rate = null;
		Boolean favourite = null;
		Integer quantity = null;
		Double total = null;
		Double shippingCost = null;
		Integer index = null;
		String cardNumber = null;
		Date expireDate = null;
		Boolean defaultSearch = null;
		Integer fav = null;
		String buyLink = null;

		List<String> buyLinks = null;
		Map<String, String> urlMap = null;

		String url = null;
		String target = null;
		Boolean redirect = false;
		Boolean send = true;

		Boolean game = null;

		if(Actions.SEARCH_PRODUCTS.equalsIgnoreCase(action)) {

			c = new Criteria();

			categoryId = ValidationUtils.parseIntParameter(request, ParameterNames.CATEGORY_ID);
			name = ValidationUtils.parameterIsEmpty(request, ParameterNames.NAME);
			if(name==null) {
				name = ValidationUtils.parameterIsEmpty(request, ParameterNames.SEARCH_BOX);
			}
			minPrice = ValidationUtils.parseDoubleParameter(request, ParameterNames.MIN_PRICE);
			maxPrice = ValidationUtils.parseDoubleParameter(request, ParameterNames.MAX_PRICE);
			minDate = ValidationUtils.parameterDateFormat(request, ParameterNames.MIN_DATE);
			maxDate = ValidationUtils.parameterDateFormat(request, ParameterNames.MAX_DATE);
			favs = ValidationUtils.parseIntParameter(request, ParameterNames.FAVS);
			rating = ValidationUtils.parseIntParameter(request, ParameterNames.RATING);
			sellerId = ValidationUtils.parseIntParameter(request, ParameterNames.SELLER_ID);
			sellerType = ValidationUtils.parseIntParameter(request, ParameterNames.SELLER_TYPE);
			minPublicationYear = ValidationUtils.parseIntParameter(request, ParameterNames.MIN_PUBLICATION_YEAR);
			maxPublicationYear = ValidationUtils.parseIntParameter(request, ParameterNames.MAX_PUBLICATION_YEAR);
			format = ValidationUtils.parseIntParameter(request, ParameterNames.FORMAT);
			coverType = ValidationUtils.parseIntParameter(request, ParameterNames.COVER_TYPE);

			page = WebUtils.getPage(request, ParameterNames.PAGE);
			startIndex = (page-1)*count+1;

			language = SessionManager.get(request, WebConstants.USER_LOCALE).toString();

			defaultSearch = ValidationUtils.parseBooleanParameter(request, ParameterNames.DEFAULT);

			if(categoryId!=null) {
				c.setIdCategoria(categoryId);
			}
			if(name!=null) {
				c.setNombre(name.toUpperCase());
			}
			if(minPrice!=null) {
				c.setPrecioDesde(minPrice);
			}
			if(maxPrice!=null) {
				c.setPrecioHasta(maxPrice);
			}
			if(minDate!=null) {
				c.setFechaMinima(minDate);
			}
			if(maxDate!=null) {
				c.setFechaMaxima(maxDate);
			}
			if(favs!=null) {
				c.setNumeroFavoritos(favs);
			}
			if(rating!=null) {
				c.setValoracion(rating);
			}
			if(sellerId!=null) {
				c.setIdVendedor(sellerId);
			}
			if(sellerType!=null) {
				c.setTipoVendedor(sellerType);
			}
			if(minPublicationYear!=null) {
				c.setAnhoPublicacionMinimo(minPublicationYear);
			}
			if(maxPublicationYear!=null) {
				c.setAnhoPublicacionMaximo(maxPublicationYear);
			}
			if(format!=null) {
				c.setFormato(format);
			}
			if(coverType!=null) {
				c.setTipoTapa(coverType);
			}

			game = SelectClass.game(c);

			if(game) {
				gs = new ArrayList<Juego>();
				games = new Results<Juego>(gs, startIndex, count);
				try {
					games = service.findJuegoByCriteria(c, startIndex, count, language);
					gs = games.getPage();
					total = Math.ceil((double)games.getTotal());
					totalPages = (int) Math.ceil((double)games.getTotal()/(double)count);

				} catch (Exception e) {
					e.printStackTrace();
					errors.add(ParameterNames.GAME, ErrorCodes.NOT_FOUND_OBJECT);
				}
			}else {
				products = new Results<Producto>(ps, startIndex, count);
				try {
					products = service.findByCriteria(c, language, startIndex, count);
					ps = products.getPage();
					total = Math.ceil((double)products.getTotal());
					totalPages = (int) Math.ceil((double)products.getTotal()/(double)count);
				} catch (Exception e) {
					e.printStackTrace();
					errors.add(ParameterNames.PRODUCT, ErrorCodes.NOT_FOUND_OBJECT);
				}
			}
			if(errors.hasErrors()) {
				target = ViewPaths.HOME;
				redirect = true;

				SetAttribute.setErrors(request, errors);
			}else {
				if(defaultSearch==true) {
					target = ViewPaths.HOME;
				} else {
					target = ViewPaths.PRODUCTS_FINDER;
				}
				redirect = false;
				if(game) {
					SetAttribute.setResults(request, gs);

				}else {
					SetAttribute.setResults(request, ps);
				}
				firstPagedPage = Math.max(1, page-pagingPageCount);
				lastPagedPage = Math.min(totalPages, page+pagingPageCount);
				url = ParameterUtils.criteriaURLBuilder(c, defaultSearch, request);
				SetAttribute.setOthers(request, ParameterNames.TOTAL, total);
				SetAttribute.setOthers(request, ParameterNames.PAGE, page);
				SetAttribute.setOthers(request, AttributeNames.TOTAL_PAGES, totalPages);
				SetAttribute.setOthers(request, AttributeNames.FIRST_PAGED_PAGE, firstPagedPage);
				SetAttribute.setOthers(request, AttributeNames.LAST_PAGED_PAGE, lastPagedPage);
				SetAttribute.setOthers(request, AttributeNames.URL, url);
			}
		}

		else if(Actions.DETAIL_VIEW.equalsIgnoreCase(action)) {

			language = SessionManager.get(request, WebConstants.USER_LOCALE).toString();

			id = ValidationUtils.parseIntParameter(request, ParameterNames.ID);

			if(id!=null) {
				try {
					g = service.findJuegoById(id);
					game = true;
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(g==null) {
					try {
						p = service.findById(id, language);
						game = false;
					} catch (Exception e) {
						e.printStackTrace();
						errors.add(ParameterNames.PRODUCT, ErrorCodes.FINDER_ERROR);
					}
				}
			}else {
				errors.add(ParameterNames.ID, ErrorCodes.MANDATORY_PARAMETER);
			}
			if(errors.hasErrors()) {
				target = ViewPaths.HOME;
				redirect = false;
				SetAttribute.setErrors(request, errors);
			}else {
				if(g!=null) {
					SetAttribute.setResult(request, g);
					target = ViewPaths.PRODUCT_DETAIL;
				}else {
					SetAttribute.setResult(request, p);
					target = ViewPaths.PRODUCT_DETAIL;
				}
				SetAttribute.setOthers(request, ParameterNames.GAME, game);
				redirect = false;
			}
		}

		else if(Actions.CREATE_COMMENT.equalsIgnoreCase(action)) {

			u = (Usuario) SessionManager.get(request, AttributeNames.USER);

			id = ValidationUtils.parseIntParameter(request, ParameterNames.ID);
			content = ValidationUtils.parameterIsEmpty(request, ParameterNames.CONTENT);

			if(u!=null) {
				comment = new Comentario();

				if(id!=null && content!=null) {
					comment.setProducto(id);
					comment.setContenido(content);
					comment.setUsuario(u.getIdUsuario());
					try {
						commentService.create(comment);
					} catch (Exception e) {
						e.printStackTrace();
						errors.add(ParameterNames.COMMENT, ErrorCodes.CREATE_COMMENT_ERROR);
					}
				}else {
					if(id==null) {
						errors.add(ParameterNames.ID, ErrorCodes.MANDATORY_PARAMETER);
					}
					if(content==null) {
						errors.add(ParameterNames.CONTENT, ErrorCodes.MANDATORY_PARAMETER);
					}
				}
			}else {
				errors.add(ParameterNames.USER, ErrorCodes.NOT_FOUND_OBJECT);
				target = ViewPaths.LOGIN;
				redirect = false;
			}
			if(errors.hasErrors()) {
				if(target==null) {
					send = false;
				}else {
					redirect = false;
					SetAttribute.setErrors(request, errors);
				}
			}else {
				send = false;
			}
		}

		else if(Actions.DELETE_COMMENT.equalsIgnoreCase(action)) {

			u = (Usuario) SessionManager.get(request, AttributeNames.USER);
			if(u!=null) {
				id = ValidationUtils.parseIntParameter(request, ParameterNames.ID);
				if(id!=null) {
					try {
						commentService.delete(id);
					} catch (Exception e) {
						e.printStackTrace();
						errors.add(ParameterNames.COMMENT, ErrorCodes.REMOVE_ERROR);
					}
				}else {
					errors.add(ParameterNames.ID, ErrorCodes.MANDATORY_PARAMETER);
				}
			} else {
				errors.add(ParameterNames.USER, ErrorCodes.NOT_FOUND_OBJECT);
				target = ViewPaths.LOGIN;
			}
			if(errors.hasErrors()) {
				if(target==null) {
					send = false;
				}else {
					redirect = false;
					SetAttribute.setErrors(request, errors);
				}
			}else {
				send = false;
			}
		}

		else if(Actions.FAVOURITE.equalsIgnoreCase(action)) {

			u = (Usuario) SessionManager.get(request, AttributeNames.USER);			

			if(u!=null) {
				f = new Favorito();
				userId = u.getIdUsuario();
				id = ValidationUtils.parseIntParameter(request, ParameterNames.ID);
				rate = ValidationUtils.parseDoubleParameter(request, ParameterNames.RATE);
				favourite = ValidationUtils.parseBooleanParameter(request, ParameterNames.FAVOURITE);

				if(rate!=null) {
					f.setValoracion(rate);
				}
				f.setUsuario(userId);
				f.setProducto(id);
				if(rate==null && favourite==false) {
					errors.add(ParameterNames.FAVOURITE, ErrorCodes.MANDATORY_PARAMETER);
					errors.add(ParameterNames.RATE, ErrorCodes.MANDATORY_PARAMETER);
				}
				if(!errors.hasErrors()) {
					Favorito exist = null;
					try {
						exist = favouriteService.exist(userId, id);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if(exist!=null) {
						if(favourite) {
							if(exist.getFavorito()) {
								fav = 0;

							} else {
								fav = 1;
							}
						}
						try {
							favouriteService.update(f, fav);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						try {
							if(favourite) {
								fav = 1;
							} else {
								fav = 0;
							}
							favouriteService.create(f, fav);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				send = false;
			} else {
				target = ViewPaths.LOGIN;
				redirect = true;
			}
		}

		else if(Actions.ADD_TO_CART.equalsIgnoreCase(action)) {

			u = (Usuario) SessionManager.get(request, AttributeNames.USER);

			if(u!=null) {
				cart = (ShoppingCart) SessionManager.get(request, AttributeNames.SHOPPING_CART);
				if(cart == null) {
					cart = new ShoppingCart();
				}

				cartLines = cart.getLine();

				id = ValidationUtils.parseIntParameter(request, ParameterNames.ID);
				if(id!=null) {
					try {
						g = service.findJuegoById(id);
					} catch(Exception e) {

					}
					if(g == null) {
						language = SessionManager.get(request, WebConstants.USER_LOCALE).toString();
						try {
							p = service.findById(id, language);
						}catch(Exception e) {
							errors.add(ParameterNames.PRODUCT, ErrorCodes.FINDER_ERROR);
						}
						if(p!=null && !errors.hasErrors()) {

							total = cart.getTotal();
							shippingCost = cart.getShippingCosts();

							if(cartLines!=null) {
								ShoppingCartLine scl = new ShoppingCartLine();
								scl.setProduct(p);
								index = cartLines.indexOf(scl);
								if(index!=-1) {
									cartLine = cartLines.get(index);
									quantity = cartLine.getQuantity();
									quantity++;
									cartLine.setQuantity(quantity);
									if(total!=null) {
										total = total-cart.getShippingCosts()+p.getPrecio();
									} else {
										total = p.getPrecio()*quantity;
									}
									shippingCost = 0.5;
								}
							} 
							if(index == null || index == -1){
								if(cartLines == null) {
									cartLines = new ArrayList<ShoppingCartLine>();
								}
								quantity = 1;
								cartLine = new ShoppingCartLine();
								cartLine.setProduct(p);
								cartLine.setQuantity(quantity);
								cartLine.setGame(false);
								cartLines.add(cartLine);
								if(total==null) {
									total = p.getPrecio()*quantity;
								} else {
									total = total-cart.getShippingCosts()+p.getPrecio();
								}
								shippingCost = quantity*0.5;
							}
						}
					} if (g!=null && !errors.hasErrors()){
						total = cart.getTotal();
						shippingCost = cart.getShippingCosts();

						if(cartLines!=null) {
							ShoppingCartLine scl = new ShoppingCartLine();
							scl.setProduct(g);
							index = cartLines.indexOf(scl);
							if(index!=-1) {
								cartLine = cartLines.get(index);
								quantity = cartLine.getQuantity();
								quantity++;
								cartLine.setQuantity(quantity);
								if(total!=null) {
									total = total-cart.getShippingCosts()+g.getPrecio();
								} else {
									total = g.getPrecio()*quantity;
								}
								if(g.getFormato().equals(0)){
									shippingCost = 0.5;
								} else {
									shippingCost = 0.0;
								}
							}
						} 
						if(index == null || index == -1){
							if(cartLines == null) {
								cartLines = new ArrayList<ShoppingCartLine>();
							}
							quantity = 1;
							cartLine = new ShoppingCartLine();
							cartLine.setProduct(g);
							cartLine.setQuantity(quantity);
							cartLine.setGame(true);
							cartLines.add(cartLine);
							if(total!=null) {
								total = total-cart.getShippingCosts()+g.getPrecio()*quantity;
							} else {
								total = g.getPrecio()*quantity;
							}
							if(g.getFormato().equals(0)){
								shippingCost = 0.5;
							} else {
								shippingCost = 0.0;
							}
						}
					}
					if(!errors.hasErrors()) {
						cart.setLine(cartLines);
						if(cart.getShippingCosts()!=null) {
							cart.setShippingCosts(shippingCost+cart.getShippingCosts());
						} else {
							cart.setShippingCosts(shippingCost);
						}
						logger.debug(shippingCost + " " +total);
						total =  total+shippingCost;
						cart.setTotal(total);
						SessionManager.set(request, AttributeNames.SHOPPING_CART, cart);
					}
				} else {
					errors.add(ParameterNames.ID, ErrorCodes.MANDATORY_PARAMETER);
				} send = false;
			} else {
				target = ViewPaths.LOGIN;
				redirect = true;
			}
		}

		else if(Actions.REMOVE_FROM_CART.equalsIgnoreCase(action)) {

			u = (Usuario) SessionManager.get(request, AttributeNames.USER);

			if(u!=null) {
				cart = (ShoppingCart) SessionManager.get(request, AttributeNames.SHOPPING_CART);

				cartLines = cart.getLine();

				id = ValidationUtils.parseIntParameter(request, ParameterNames.ID);
				if(id!=null && cartLines!=null) {
					try {
						g = service.findJuegoById(id);
					} catch(Exception e) {

					}
					if(g == null) {
						language = SessionManager.get(request, WebConstants.USER_LOCALE).toString();
						try {
							p = service.findById(id, language);
						}catch(Exception e) {
							errors.add(ParameterNames.PRODUCT, ErrorCodes.FINDER_ERROR);
						}
						if(p!=null && !errors.hasErrors()) {
							ShoppingCartLine scl = new ShoppingCartLine();
							scl.setProduct(p);
							index = cartLines.indexOf(scl);
							if(index!=-1) {
								total = cart.getTotal();
								shippingCost = cart.getShippingCosts();
								p = (Producto) cartLines.get(index).getProduct();
								quantity = cartLines.get(index).getQuantity();
								total = total-p.getPrecio()*quantity;
								shippingCost = shippingCost-quantity*0.5;
								cartLines.remove(scl);
							}
						}
					} if(g!=null && !errors.hasErrors()) {
						ShoppingCartLine scl = new ShoppingCartLine();
						scl.setProduct(g);
						index = cartLines.indexOf(scl);
						if(index!=-1) {
							total = cart.getTotal();
							shippingCost = cart.getShippingCosts();
							g = (Juego) cartLines.get(index).getProduct();
							quantity = cartLines.get(index).getQuantity();
							total = total-g.getPrecio()*quantity;
							if(g.getFormato().equals(0)) {
								shippingCost = cart.getShippingCosts()-quantity*0.5;
							} else {
								shippingCost = cart.getShippingCosts();
							}
							cartLines.remove(scl);
						}
					}

					if(!errors.hasErrors()) {
						cart.setTotal(total);
						cart.setShippingCosts(shippingCost);
						cart.setLine(cartLines);
						SessionManager.set(request, AttributeNames.SHOPPING_CART, cart);
					}
				} else {
					errors.add(ParameterNames.ID, ErrorCodes.MANDATORY_PARAMETER);
				}
			} else {
				target = ViewPaths.LOGIN;
				redirect = true;
			}
			send = false;
		}

		else if(Actions.CLEAR_CART.equalsIgnoreCase(action)) {

			u = (Usuario) SessionManager.get(request, AttributeNames.USER);

			if(u!=null) {
				cart = new ShoppingCart();
				SessionManager.set(request, AttributeNames.SHOPPING_CART, cart);
				send = false;
			} else {
				target = ViewPaths.LOGIN;
				redirect = true;
			}
			
		}

		else if(Actions.MODIFY_QUANTITY.equalsIgnoreCase(action)) {

			u = (Usuario)SessionManager.get(request, AttributeNames.USER);
			send = false;
			if(u!=null) {

				cart = (ShoppingCart) SessionManager.get(request, AttributeNames.SHOPPING_CART);

				cartLines = cart.getLine();

				index = ValidationUtils.parseIntParameter(request, ParameterNames.ID);
				if(index!=null) {
					quantity = ValidationUtils.parseIntParameter(request, ParameterNames.QUANTITY);
					if(quantity!=null) {
						if(quantity!=0) {
							cartLines.get(index).setQuantity(quantity);
						} else {
							target = ControllerPaths.NO_CONTEXT_PRODUCTO.concat("?").concat(ParameterNames.ACTION)
									.concat("=").concat(Actions.REMOVE_FROM_CART);
							send = true;
						}
					}
					SessionManager.set(request, AttributeNames.SHOPPING_CART, cart);
					send = false;
				} 
			} else {
				target = ViewPaths.LOGIN;
				redirect = true;
			}
		}

		else if(Actions.PRE_BUY.equalsIgnoreCase(action)) {
			
			u = (Usuario) SessionManager.get(request, AttributeNames.USER);
			
			if(u!=null) {
				cardNumber = ValidationUtils.parameterIsEmpty(request, ParameterNames.CARD_NUMBER);
				expireDate = ValidationUtils.dateFormat(ValidationUtils.getParameter(request, ParameterNames.EXPIRE_DATE));
				
				if(cardNumber!=null && expireDate!=null) {
					if(PagoServiceMock.check(cardNumber, expireDate)) {
						urlMap = new HashMap<String, String>();
						urlMap.put(ParameterNames.ACTION, Actions.BUY);
						target = ParameterUtils.URLBuilder(ControllerPaths.NO_CONTEXT_PRODUCTO, urlMap);
						redirect = true;
					} else {
						target = ViewPaths.CART;
					}
				} else {
					target = ViewPaths.CART;
				}
			} else {
				target = ViewPaths.LOGIN;
				redirect = true;
			}
		}

		else if(Actions.BUY.equalsIgnoreCase(action)) {

			u = (Usuario)SessionManager.get(request, AttributeNames.USER);

			if(u!= null) {
				cart = (ShoppingCart)SessionManager.get(request, AttributeNames.SHOPPING_CART);
				if(cart!=null) {
					buyLinks = new ArrayList<String>();
					urlMap = new HashMap<String, String>();
					cartLines = cart.getLine();
					for(ShoppingCartLine line:cartLines) {
						game = line.getGame();
						if(game) {
							g = (Juego) line.getProduct();
							id = g.getIdProducto();
							total = g.getPrecio();
							urlMap.put(ParameterNames.ACTION, Actions.DOWNLOAD);
							urlMap.put(ParameterNames.ID, id.toString());
							buyLink = ParameterUtils.URLBuilder(ControllerPaths.PRODUCTO, urlMap);
							buyLinks.add(buyLink);
						} else {
							p = (Producto) line.getProduct();
							id = p.getIdProducto();
							total = p.getPrecio();
						}
						buyLine = new LineaCompra();
						quantity = line.getQuantity();
						buyLine.setCantidad(quantity);
						buyLine.setIdProducto(id);
						buyLine.setPrecioUnitario(total);
						buyLine.setPrecioTotal(total);
						buyLine.setDescuento(0.0);
						buyLines = new ArrayList<LineaCompra>();
						buyLines.add(buyLine);
					}
					logger.debug(cart.getTotal());
					logger.debug(cart.getShippingCosts());
					logger.debug(cart.getTotal()-cart.getShippingCosts());
					buy = new Compra();
					buy.setIdUsuario(u.getIdUsuario());
					buy.setGastosEnvio(cart.getShippingCosts());
					buy.setSubtotal(cart.getTotal()-cart.getShippingCosts());
					buy.setTotal(cart.getTotal());
					buy.setModoPago(1);
					try {
						buyService.create(buy, buyLines);
					} catch (Exception e) {
						
						e.printStackTrace();
					}
				} else {
					errors.add(ParameterNames.CART, ErrorCodes.MANDATORY_PARAMETER);
				}
				if(errors.hasErrors()) {
					target = ViewPaths.HOME;
					redirect = true;
				} else {
					SetAttribute.setOthers(request, ParameterNames.URL_DOWNLOAD, buyLinks);
					redirect = false;
					target = ViewPaths.DOWNLOAD;
				}
			} else {
				target = ViewPaths.LOGIN;
				redirect = true;
			}
		}

		else if(Actions.DOWNLOAD.equalsIgnoreCase(action)) {

			u = (Usuario) SessionManager.get(request, AttributeNames.USER);

			if(u!=null) {
				name = ValidationUtils.parameterIsEmpty(request, ParameterNames.ID);
				FileUtils.readDocument(response, name);
				return;
			} else {
				target = ViewPaths.LOGIN;
				redirect = true;
			}
		}
		RedirectOrForward.send(request, response, redirect, target, send);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
