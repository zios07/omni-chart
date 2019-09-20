$(function(){
//	$('.nomEntites').hide();
	$('.nomCollaborateurs').hide();	
//	$('.entiteMere').click(function(){
//		$('.nomCollaborateurs').hide();
//		$('.nomEntites').slideToggle();
//	});
	$('.equipeLink').click(function(){
		var id_div = $(this).attr('id');
		$('#collaborateurs_'+id_div).slideToggle();
	});
	$('.nomEntites').click(function(){
		var ids= null;
		
		// Recupération du nom de l'entité cliquée
		var clicked_ID = $(this).attr('id');
		var res = clicked_ID.split("_");
		var clicked_entity_name = res[0];		
//		alert(clicked_entity_name);

		$('.nomEntites').each(function(){
			ids = $(this).attr('id');
			var res2 = ids.split("_");
			var entite_mere_des_entites = res2[1];
			if( entite_mere_des_entites == clicked_entity_name){
				$('#'+ids).slideToggle();
			}
			
		});
		

	});
});
