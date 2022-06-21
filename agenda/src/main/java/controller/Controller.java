package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DAO;
import model.JavaBeans;

@WebServlet(urlPatterns = { "/Controller", "/main","/insert","/select"})
public class Controller extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	DAO dao = new DAO();
	JavaBeans contato = new JavaBeans();

	public Controller() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		//System.out.println(action);
		if (action.equals("/main")) {
			contatos(request, response);
		}else if(action.equals("/insert")) {
			novoContato(request,response);
		}else if(action.equals("/select")) {
			listarContato(request,response);	
		}else {
			response.sendRedirect("/index.html");
		}
		
//		//teste de conex�o
//		dao.testeConexao();
	}

	// Listar contatos
	protected void contatos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//Criando um objeto que ir� receber os dados JavaBeans
		ArrayList<JavaBeans> lista=dao.listarContatos();
		//teste de recebimento da lista
//		for (int i = 0; i < lista.size(); i++) {
//			System.out.println(lista.get(i).getIdcon());
//			System.out.println(lista.get(i).getNome());
//			System.out.println(lista.get(i).getFone());
//			System.out.println(lista.get(i).getEmail());}
//		//Encaminhar a lista ao documento agenda.jsp
		request.setAttribute("contatos",lista);
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		rd.forward(request, response);
	}
	
	// Novo contato
		protected void novoContato(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			//teste de recebimento dos dados do formul�rio
//			System.out.println(request.getParameter("nome"));
//			System.out.println(request.getParameter("fone"));
//			System.out.println(request.getParameter("email"));
			//setar as vari�veis JavaBeans
			contato.setNome(request.getParameter("nome"));
			contato.setFone(request.getParameter("fone"));
			contato.setEmail(request.getParameter("email"));
			//invocar o m�todo inserirContato passando o objeto contato
			dao.inserirContato(contato);
			//redirecionar para o documento agenda.jsp
			response.sendRedirect("main");
		}
	//Editar Contato	
		protected void listarContato(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException	{
			//Recebimento do id do contato que ser� editado	
			String idcon = request.getParameter("idcon");
			//Teste de recebimento
			//System.out.println(idcon);	
			contato.setIdcon(idcon);
			//Executar o m�todo selecionarContato da classe DAO
			dao.selecionarContato(contato);
//			//Teste de recebimento
//			System.out.println(contato.getIdcon());
//			System.out.println(contato.getNome());
//			System.out.println(contato.getFone());
//			System.out.println(contato.getEmail());
			//Setar os atributos do formul�rio com o conte�do JavaBeans
			request.setAttribute("idcon", contato.getIdcon());
			request.setAttribute("nome", contato.getNome());
			request.setAttribute("fone", contato.getFone());
			request.setAttribute("email", contato.getEmail());
			//Encaminhar ao documento editar.jsp
			RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
			rd.forward(request, response);
		}
}
