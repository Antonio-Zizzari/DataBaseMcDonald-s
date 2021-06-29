package senzascanner;
import java.io.IOException;
import java.sql.*;

public class McDrive {
	
	public McDrive() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch (ClassNotFoundException e) {
			System.err.println("Driver non caricato");
		}
	}
	
	private Connection getConnection() throws SQLException{
		String url = "jdbc:mysql://localhost:3306/mcdrive?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
		Connection con = DriverManager.getConnection(url,"mcdrive","mcdrive");
		return con;
	}
	
	private void releaseConnection(Connection con) throws SQLException{
		if(con != null) {
			con.close();
			con = null;
		}
	}
	
	
	private void op1()  throws SQLException{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			con = this.getConnection();
			con.setAutoCommit(false);
			
			String query = "insert into account(punti,mail) values(?,?)";
			ps = con.prepareStatement(query);
			
			String mail = "simonegiglio@hotmail.com";
			int punti = 128;
			ps.setInt(1,punti);
			ps.setString(2,mail);
			
	
			if(ps.executeUpdate() > 0)
				System.out.println("Query correttamente eseguita\n");
			else
				System.err.println("Errore Query");
			//scan.close();
			con.commit();
			con.setAutoCommit(true);
		}
		catch(SQLException e){
			System.err.println(e.getMessage());
			con.rollback();
		}
		finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				this.releaseConnection(con);
			}
			catch(SQLException c) {
				System.err.println(c.getMessage());
			}
		}
	}
	
	private void op2()  throws SQLException{
		Connection con = null;
		PreparedStatement ps = null,ps1=null,ps2=null;
		ResultSet rs = null;
		
		try {
			con = this.getConnection();
			con.setAutoCommit(false);
			//Parte 1
			String query = "insert into scontrino(data,numero,mail,mc_nome) values(?,?,?,?)";
			ps = con.prepareStatement(query);
			
			String data = "2019-07-11";
			int numero = 1;
			String mail = "simonegiglio@hotmail.com";
			String mcdrive = "McDrive Napoli";
			
			ps.setString(1,data);
			ps.setInt(2,numero);
			ps.setString(3,mail);
			ps.setString(4,mcdrive);
			
			
			
			if(ps.executeUpdate() > 0)
				System.out.println("Query correttamente eseguita\n");
			else
				System.err.println("Errore Query");
			//Parte 2
			query = "update cliente set num_scontrini = num_scontrini+1 where mail = ?";
			ps1 = con.prepareStatement(query);			
			
			ps1.setString(1,mail);
			
			if(ps1.executeUpdate() > 0)
				System.out.println("Aggiornamento cliente fatto con successo\n");
			else
				System.err.println("Errore aggiornamento cliente");
			//Parte 3
			query = "insert into possiede(data,numero,id,quantita) values(?,?,?,?)";
			ps2 = con.prepareStatement(query);
			
			int id = 14;
			int quantita = 3;
			
			ps2.setString(1,data);
			ps2.setInt(2,numero);
			ps2.setInt(3,id);
			ps2.setInt(4,quantita);
			
			if(ps2.executeUpdate() > 0)
				System.out.println("Query correttamente eseguita\n");
			else
				System.err.println("Errore Query");
			
			con.commit();
			con.setAutoCommit(true);
		}
		catch(SQLException e){
			System.err.println(e.getMessage());
			con.rollback();
		}
		finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				if(ps1!=null)	ps.close();
				if(ps2!=null)	ps.close();
				this.releaseConnection(con);
			}
			catch(SQLException c) {
				System.err.println(c.getMessage());
			}
		}
	}

	private void op3()  throws SQLException{
		Connection con = null;
		PreparedStatement ps = null,ps1=null,ps2=null,ps3=null;
		ResultSet rs = null;
		
		try {
			con = this.getConnection();
			con.setAutoCommit(false);
			
			//Parte 1
			String query = "DELETE FROM cuochi where cf = ?";
			ps = con.prepareStatement(query);
			String cf = "IIYKHJKVKBFH";
			
			ps.setString(1,cf);			
			
			if(ps.executeUpdate() > 0)
				System.out.println("Cuoco correttamente eliminato\n");
			else
				System.err.println("Errore Eliminazione cuoco");
			
			//Parte 2.1
			String mcnome;
			query="select mc_nome from dipendenti where cf = ?";
			ps1 = con.prepareStatement(query);
			ps1.setString(1,cf);	
			rs=ps1.executeQuery();
			if(rs.next()) {
				mcnome=rs.getString("mc_nome");
			}
			else {
				mcnome="";
			}		
			//Parte2.2
			query = "DELETE FROM dipendenti where cf = ?";
			ps2 = con.prepareStatement(query);			
			
			ps2.setString(1,cf);
			
			if(ps2.executeUpdate() > 0)
				System.out.println("Aggiornamento cliente fatto con successo\n");
			else
				System.err.println("Errore cancellazione cuoco");
			//Parte 3
			
			query = "update mcdrive set num_dipendenti= num_dipendenti-1 where nome = ?";
			ps3 = con.prepareStatement(query);			
			
			ps3.setString(1,mcnome);
			
			if(ps3.executeUpdate() > 0)
				System.out.println("Aggiornamento cliente fatto con successo\n");
			else
				System.err.println("Errore aggiornamento mcdrive");
			
			
			con.commit();
			con.setAutoCommit(true);
		}
		catch(SQLException e){
			System.err.println(e.getMessage());
			con.rollback();
		}
		finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				if(ps1!=null)	ps.close();
				if(ps2!=null)	ps.close();
				if(ps3!=null)	ps.close();
				this.releaseConnection(con);
			}
			catch(SQLException c) {
				System.err.println(c.getMessage());
			}
		}
	}
	
	private void op4()  throws SQLException{
		Connection con = null;
		PreparedStatement ps = null,ps1=null,ps2=null,ps3=null;
		ResultSet rs = null;
		
		try {
			con = this.getConnection();
			con.setAutoCommit(false);
			//Parte 1
			String query = "insert into cliente(mail,nome,cognome,num_scontrini) values(?,?,?,1)";
			ps = con.prepareStatement(query);
			
			String mail = "prova123@gmail.com";
			String nome = "Mario";
			String cognome = "Rossi";
			
			ps.setString(1,mail);	
			ps.setString(2,nome);
			ps.setString(3,cognome);
			
			if(ps.executeUpdate() > 0)
				System.out.println("Query 4 correttamente eseguita\n");
			else
				System.err.println("Errore Query");
			
			//Op2
			
			query = "insert into scontrino(data,numero,mail,mc_nome) values(?,?,?,?)";
			ps1 = con.prepareStatement(query);
			
			String data = "2020-01-24";
			int numero = 1;
			String mcdrive = "McDrive Napoli";
			
			ps1.setString(1,data);
			ps1.setInt(2,numero);
			ps1.setString(3,mail);
			ps1.setString(4,mcdrive);
			
			
			
			if(ps1.executeUpdate() > 0)
				System.out.println("Query 4 correttamente eseguita\n");
			else
				System.err.println("Errore Query");
			//Parte 2
			query = "update cliente set num_scontrini = num_scontrini+1 where mail = ?";
			ps2 = con.prepareStatement(query);			
			
			ps2.setString(1,mail);
			
			if(ps2.executeUpdate() > 0)
				System.out.println("Aggiornamento cliente fatto con successo\n");
			else
				System.err.println("Errore aggiornamento cliente");
			//Parte 3
			query = "insert into possiede(data,numero,id,quantita) values(?,?,?,?)";
			ps3 = con.prepareStatement(query);
			
			int id = 28;
			int quantita = 2;
			
			ps3.setString(1,data);
			ps3.setInt(2,numero);
			ps3.setInt(3,id);
			ps3.setInt(4,quantita);
			
			if(ps3.executeUpdate() > 0)
				System.out.println("Query 4 correttamente eseguita\n");
			else
				System.err.println("Errore Query");
			
			con.commit();
			con.setAutoCommit(true);
		}
		catch(SQLException e){
			System.err.println(e.getMessage());
			con.rollback();
		}
		finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				if(ps1!=null)	ps.close();
				if(ps2!=null)	ps.close();
				if(ps3!=null)	ps.close();
				this.releaseConnection(con);
			}
			catch(SQLException c) {
				System.err.println(c.getMessage());
			}
		}
	}

	private void op5()  throws SQLException{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			con = this.getConnection();
			con.setAutoCommit(false);
			
			String query = "insert into prodotti(nome,prezzo) values(?,?)";
			ps = con.prepareStatement(query);
			
			String nome = "McFlurry Banana";
			int prezzo = 3;
			ps.setString(1,nome);
			ps.setInt(2,prezzo);
			
			if(ps.executeUpdate() > 0)
				System.out.println("Query 5 correttamente eseguita\n");
			else
				System.err.println("Errore Query");
			//scan.close();
			con.commit();
			con.setAutoCommit(true);
		}
		catch(SQLException e){
			System.err.println(e.getMessage());
			con.rollback();
		}
		finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				this.releaseConnection(con);
			}
			catch(SQLException c) {
				System.err.println(c.getMessage());
			}
		}
	}
	
	private void op6()  throws SQLException{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			con = this.getConnection();
			
			String query = "select mail, num_scontrini from cliente order by num_scontrini";
			ps = con.prepareStatement(query);
			rs=ps.executeQuery();
			while(rs.next()) {
				String mail=rs.getString("mail");
				int num_s = rs.getInt("num_scontrini");
				System.out.println("mail = " + mail + " numero scontrini = " +num_s);
			}
			System.out.println();
			//scan.close();
		}
		catch(SQLException e) {
			System.err.println(e.getMessage());
		}
		finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				this.releaseConnection(con);
			}
			catch(SQLException c) {
				System.err.println(c.getMessage());
			}
		}
	}
	
	private void op7()  throws SQLException{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			con = this.getConnection();
			
			String query = "select * from scontrino where year(data)= ?";
			ps = con.prepareStatement(query);
			
			String data = "2020";
			ps.setString(1, data);
			rs = ps.executeQuery();
			while(rs.next()) {
				String data1=rs.getString("data");
				int numero= rs.getInt("numero");		
				String mail=rs.getString("mail");
				String mc_nome=rs.getString("mc_nome");
				System.out.println("data = " + data1 +" numero = "+numero+" mail = "+mail+" McDrive = "+mc_nome);
			}
			System.out.println();
			
			//scan.close();
		}
		catch(SQLException e) {
			System.err.println(e.getMessage());
		}
		finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				this.releaseConnection(con);
			}
			catch(SQLException c) {
				System.err.println(c.getMessage());
			}
		}
	}
	
	private void op8()  throws SQLException{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			con = this.getConnection();
			
			String query = "select nome from mcdrive where num_dipendenti between ? and ?";
			ps = con.prepareStatement(query);
			
			int min = 3;
			int max = 6;
			ps.setInt(1, min);
			ps.setInt(2, max);
			rs = ps.executeQuery();
			while(rs.next()) {
				String nome=rs.getString("nome");
				System.out.println("nome = " + nome);
			}
			System.out.println();
			//scan.close();
		}
		catch(SQLException e) {
			System.err.println(e.getMessage());
		}
		finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				this.releaseConnection(con);
			}
			catch(SQLException c) {
				System.err.println(c.getMessage());
			}
		}
	}
	
	private void op9()  throws SQLException{
		Connection con = null;
		PreparedStatement ps = null,ps1=null,ps2=null,ps3=null;
		ResultSet rs = null;
		
		try {
			con = this.getConnection();
			con.setAutoCommit(false);
			//Parte 1
			String query = "insert into dipendenti(cf,nome,cognome,mc_nome) values(?,?,?,?)";
			ps = con.prepareStatement(query);
			;
			String cf = "AAAABBBBCCCC";
			String nome = "Domenico";
			String cognome = "Caprari";
			String mc_nome = "McDrive Pompei";
			
			ps.setString(1,cf);	
			ps.setString(2,nome);	
			ps.setString(3,cognome);	
			ps.setString(4,mc_nome);	
			
			if(ps.executeUpdate() > 0)
				System.out.println("Query correttamente eseguita\n");
			else
				System.err.println("Errore Query");
			
			//Parte2.2
			query = "insert into cuochi(cf,anni_esperienza) values(?,?)";
			ps2 = con.prepareStatement(query);	
			
			int anni = 3;
			
			ps2.setString(1,cf);
			ps2.setInt(2,anni);
			
			if(ps2.executeUpdate() > 0)
				System.out.println("Aggiornamento cliente fatto con successo\n");
			else
				System.err.println("Errore cancellazione cuoco");
			//Parte 3
			
			query = "update mcdrive set num_dipendenti= num_dipendenti+1 where nome = ?";
			ps3 = con.prepareStatement(query);			
			
			ps3.setString(1,mc_nome);
			
			if(ps3.executeUpdate() > 0)
				System.out.println("Aggiornamento cuoco fatto con successo\n");
			else
				System.err.println("Errore aggiornamento mcdrive");
			
			
			con.commit();
			con.setAutoCommit(true);
		}
		catch(SQLException e){
			System.err.println(e.getMessage());
			con.rollback();
		}
		finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				if(ps1!=null)	ps.close();
				if(ps2!=null)	ps.close();
				if(ps3!=null)	ps.close();
				this.releaseConnection(con);
			}
			catch(SQLException c) {
				System.err.println(c.getMessage());
			}
		}
	}

	private void op10() throws SQLException{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			con = this.getConnection();
			
			String query = "select * from prodotti";
			ps = con.prepareStatement(query);
			rs=ps.executeQuery();
			while(rs.next()) {
				int id= rs.getInt("id");
				String nome=rs.getString("nome");
				int prezzo = rs.getInt("prezzo");
				System.out.println("id = " + id + " nome = " +nome+" prezzo = "+prezzo);
			}
			System.out.println();
			//scan.close();
		}
		catch(SQLException e) {
			System.err.println(e.getMessage());
		}
		finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				this.releaseConnection(con);
			}
			catch(SQLException c) {
				System.err.println(c.getMessage());
			}
		}
	}
	
	private void op11() throws SQLException{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			con = this.getConnection();
			
			String query = "select * from cliente where mail=?";
			ps = con.prepareStatement(query);
			
			String mail = "simonegiglio@hotmail.com";
			ps.setString(1, mail);
			rs = ps.executeQuery();
			if(rs.next()) {
				String email=rs.getString("mail");
				String nome=rs.getString("nome");
				String cognome=rs.getString("cognome");
				int num_scontrini=rs.getInt("num_scontrini");
				System.out.println("mail = " + email+" nome = "+nome+" cognome = "+ cognome +" numero scontrini = "+num_scontrini);
			}
			System.out.println();
			//scan.close();
		}
		catch(SQLException e) {
			System.err.println(e.getMessage());
		}
		finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				this.releaseConnection(con);
			}
			catch(SQLException c) {
				System.err.println(c.getMessage());
			}
		}
	}
	
	private void op12() throws SQLException{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			con = this.getConnection();
			
			String query = "Select * from mcdrive where nome=?";
			ps = con.prepareStatement(query);
			
			String nome = "McDrive Afragola";
			ps.setString(1, nome);
			rs = ps.executeQuery();
			if(rs.next()) {
				String mnome=rs.getString("nome");
				int numero_dipendenti=rs.getInt("num_dipendenti");
				System.out.println("nome = " + mnome+" numero dipendenti = "+numero_dipendenti);
			}
			System.out.println();
			//scan.close();
		}
		catch(SQLException e) {
			System.err.println(e.getMessage());
		}
		finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				this.releaseConnection(con);
			}
			catch(SQLException c) {
				System.err.println(c.getMessage());
			}
		}
	}
	
	private void op13() throws SQLException{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			con = this.getConnection();
			
			String query = "select ((select sum(num_scontrini) as tot_scontrini from cliente)/(select count(numero_anni) from(select count(*) as numero_anni from scontrino group by year(data)) as numero_anni)) as media";
			ps = con.prepareStatement(query);
			rs=ps.executeQuery();
			if(rs.next()) {
				double media= rs.getDouble("media");
				System.out.println("media = " + media);
			}
			System.out.println();
			//scan.close();
		}
		catch(SQLException e) {
			System.err.println(e.getMessage());
		}
		finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				this.releaseConnection(con);
			}
			catch(SQLException c) {
				System.err.println(c.getMessage());
			}
		}
	}
	
	private void op14() throws SQLException{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			con = this.getConnection();
			
			String query = "select p.nome, max(risultato.totale) as acquisti_totali from (select possiede_d.id,sum(possiede_d.quantita) as totale from (select * from possiede where year(data)= ? and month(data)= ?) as possiede_d group by possiede_d.id) as risultato, prodotti as p where p.id=risultato.id";
			ps = con.prepareStatement(query);
			
			String anno = "2020";
			String mese = "01";
			ps.setString(1, anno);
			ps.setString(2, mese);
			rs = ps.executeQuery();
			if(rs.next()) {
				String mnome=rs.getString("nome");
				String acquisti_tot=rs.getString("acquisti_totali");
				System.out.println("nome = " + mnome+" acquisti totali = "+acquisti_tot);
			}
			System.out.println();
			//scan.close();
		}
		catch(SQLException e) {
			System.err.println(e.getMessage());
		}
		finally {
			try {
				if(rs!=null)	rs.close();
				if(ps!=null)	ps.close();
				this.releaseConnection(con);
			}
			catch(SQLException c) {
				System.err.println(c.getMessage());
			}
		}
	}

	//Main
	public static void main(String[] args) throws IOException,SQLException{
		McDrive db = new McDrive();
	
		System.out.println("Operazione 1 per far istanziare ad un cliente un nuovo account.");
		db.op1();
		System.out.println("Operazione 2 per far richiedere ad un cliente la stampa di un nuovo scontrino");
		db.op2();
		System.out.println("Operazione 3 per rimuovere un cuoco.");
		db.op3();
		System.out.println("Operazione 4 per inserire un nuovo cliente.");
		db.op4();
		System.out.println("Operazione 5 per inserire un nuovo prodotto.");
		db.op5();
		System.out.println("Operazione 6 per stampare in modo ordinato i clienti in base al numero di scontrini acquistati.");
		db.op6();
		System.out.println("Operazione 7 per stampare gli scontrini emessi in un dato anno.");
		db.op7();
		System.out.println("Operazione 8 per stampare l'elenco dei dipendenti di un McDrive compreso in un intervallo.");
		db.op8();
		System.out.println("Operazione 9 per inserire un nuovo cuoco.");
		db.op9();
		System.out.println("Operazione 10 per stampare l'elenco di tutti i prodotti.");
		db.op10();
		System.out.println("Operazione 11 per stampare il numero di scontrini di un cliente.");
		db.op11();
		System.out.println("Operazione 12 per stampare il numero di dipendenti di un McDrive.");
		db.op12();
		System.out.println("Operazione 13 per stampare la media degli scontrini acquistati dai clienti all'anno.");
		db.op13();
		System.out.println("Operazione 14 per stampare il nome del prodotto più acquistato in un dato mese.");
		db.op14();
	}
}
